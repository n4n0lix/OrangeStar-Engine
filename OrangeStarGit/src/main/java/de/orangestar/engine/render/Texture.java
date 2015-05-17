package de.orangestar.engine.render;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33;

import de.orangestar.engine.debug.DebugManager;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.resources.Resource;

public class Texture implements Resource {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               PUBLIC                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public Texture(BufferedImage img, boolean linearFiltering) {
        this( bufferedImageToByteBuffer(img), img.getWidth(), img.getHeight(), linearFiltering);
    }
    
    public Texture(ByteBuffer buffer, int width, int height, boolean linearFiltering) {
        DebugManager.Get().glClearError();
        // 1# Configure texture object
        _id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, _id);
        
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        
        if (!linearFiltering) {
          GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
          GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        }
        else {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        }
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        // 2# Load image data       
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA4, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        DebugManager.Get().glCheckError("Texture Creation");
    }
    
    /**
     * Sets this texture as the 'to use' texture. Texture unit 0 will be used.
     */
    public void link(Shader shader) {
        link(shader, 0);
    }

    /**
     * Sets this texture as the 'to use' texture.
     * @param shader The shader that to use this texture
     * @param textureSlot The texture unit slot in the shader to which this texture will be bound
     */
    public void link(Shader shader, int textureSlot) {
        GL20.glUniform1i(shader.getTexture0Location(), textureSlot);
        GL13.glActiveTexture(GL13.GL_TEXTURE0); // TODO: textureSlot
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, _id);
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              PRIVATE                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Writes the color data of a buffered image into a ByteBuffer and returns it.
     * @param image The image
     * @return A ByteBuffer that contains the color data of the image
     */
    private static ByteBuffer bufferedImageToByteBuffer(BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); //4 for RGBA, 3 for RGB

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));             // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));     // Alpha component. Only for RGBA
            }
        }

        buffer.flip();
        
        return buffer;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (_id ^ (_id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Texture other = (Texture) obj;
        if (_id != other._id)
            return false;
        return true;
    }

    private int _id;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           PRIVATE STATIC                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

}
