package de.orangestar.engine.render.shader;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;

import de.orangestar.engine.debug.DebugManager;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Vector2f;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Vertex;

/**
 * A shader defines how vertices are being displayed by OpenGL (position, color, ...).
 * 
 * @author Basti
 */
public class Shader {

    /* Shader layout */

    /* Vertex shader - uniforms */
    public static final String UniAttribNameWVP         = "uni_wvp";
    public static final String UniAttribNameTexture0    = "uni_texture0";
    
    /* Vertex shader - attributes */
    public static final String VsAttribNamePosition     = "vs_position";
    public static final int    VsAttribLocPosition      = 0;
    
    public static final String VsAttribNameColor        = "vs_color";
    public static final int    VsAttribLocColor         = 1;
    
    public static final String VsAttribNameTexCoord     = "vs_uv";
    public static final int    VsAttribLocTexCoord      = 2;
    
    public static final String VsAttribNameInstances_Position   = "vs_instance_position";
    public static final int    VsAttribLocInstances_Position_0  = 10;
    public static final int    VsAttribLocInstances_Position_1  = VsAttribLocInstances_Position_0 + 1;
    public static final int    VsAttribLocInstances_Position_2  = VsAttribLocInstances_Position_0 + 2;
    public static final int    VsAttribLocInstances_Position_3  = VsAttribLocInstances_Position_0 + 3;
    
    public static final String VsAttribNameInstances_UV         = "vs_instance_uv";
    public static final int    VsAttribLocInstances_UV          = 14;
    
    /* Fragment shader - uniforms */
    public static final String FsAttribNameColor        = "fs_color";
    public static final String FsAttribNameTexCoord     = "fs_uv";
        
    public static final String OutAttibNameColor        = "out_color";
    
    /* Default engine shaders */
    public static final Shader StreamingBatchShader = new ShaderBuilder()
                                                            .textured()
                                                            .colored()
                                                            .build();
    
    public static final Shader InstancingBatchShader = new ShaderBuilder()
//                                                            .instanced(32768)
                                                            .textured()
                                                            .colored()
                                                            .build();
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               PUBLIC                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        
    public Shader(String vertex, String fragment) {

        DebugManager.Get().info(this.getClass(), "Vertex-Shader: \n" + vertex + "\n");
        DebugManager.Get().info(this.getClass(), "Fragment-Shader: \n" + fragment + "\n");
        
        // 1# Read/Compile VertexShader
        int idVertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(idVertexShader, vertex);        
        GL20.glCompileShader(idVertexShader);

        // 1.1# Check if VertexShader has compiled
        if (GL20.glGetShaderi(idVertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile vertex shader: " + GL20.glGetShaderInfoLog(idVertexShader));
            System.exit(-1);
        }

        // 2# Read/Compile FragmentShader
        int idFragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(idFragmentShader, fragment);
        GL20.glCompileShader(idFragmentShader);

        // 2.1# Check if VertexShader has compiled
        if (GL20.glGetShaderi(idFragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile fragment shader: " + GL20.glGetShaderInfoLog(idFragmentShader));
            System.exit(-1);
        }
        
        // 3# Create Shader-Program
        _id = GL20.glCreateProgram();
        
        // 3.1# Link the shaders to the Shader-Program
        GL20.glAttachShader(_id, idVertexShader);
        GL20.glAttachShader(_id, idFragmentShader);
        GL20.glLinkProgram(_id);
        if (GL20.glGetProgrami(_id, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.out.println("Shader linking failed: " + GL20.glGetProgramInfoLog(_id));
            System.exit(-1);
        }
        GL20.glValidateProgram(_id);
        
        // 3.2# Delete the shaders to simplify later cleanup
        GL20.glDeleteShader(idVertexShader);
        GL20.glDeleteShader(idFragmentShader);
        DebugManager.Get().glCheckError("Shader-Creation");

        // 4# Get Shader Layout Attribute Locations
        _locWVP       = GL20.glGetUniformLocation(_id, Shader.UniAttribNameWVP);
        _locTexture0  = GL20.glGetUniformLocation(_id, Shader.UniAttribNameTexture0);
    }
    
    /**
     * Set this shader as the active shader.
     */
    public void bind() {
        GL20.glUseProgram(_id);
        GL20.glUniformMatrix4fv(_locWVP, 1, true, RenderManager.Get().getWVPBuffer());
    }
        
    /**
     * Don't use this shader any longer as the active shader.
     */
    public void unbind() {
        GL20.glUseProgram(0);
    }
    
    /**
     * Tells OpenGL the vertex layout of this shader. Use this when a VBO is setup.
     */
    public void layoutVBO() {
        int offset = 0;
        
        // Setup vertex position attribute
        GL20.glEnableVertexAttribArray(Shader.VsAttribLocPosition);
        GL20.glVertexAttribPointer(Shader.VsAttribLocPosition, Vector3f.ComponentsCount, GL11.GL_FLOAT, false, Vertex.ByteSize, offset); 
        offset += Vector3f.ByteSize;

        // Setup vertex color attribute
        if (_isColored) {
            GL20.glEnableVertexAttribArray(Shader.VsAttribLocColor);
            GL20.glVertexAttribPointer(Shader.VsAttribLocColor, Color4f.ComponentsCount, GL11.GL_FLOAT, false, Vertex.ByteSize, offset); 
            offset += Color4f.ByteSize;
        }
        
        // Setup vertex texcoord attribute
        if (_isTextured) {
            GL20.glEnableVertexAttribArray(Shader.VsAttribLocTexCoord);
            GL20.glVertexAttribPointer(Shader.VsAttribLocTexCoord, Vector2f.ComponentsCount, GL11.GL_FLOAT, false, Vertex.ByteSize, offset); 
            offset += Vector2f.ByteSize;
        }
    }
    
    /**
     * Tells OpenGL the instances layout of this shader. Use this when a BufferObject for instanced rendering is setup.
     */
    public void layoutInstancedRendering() {
        if (_isInstanced) {
            // Setup the transform attribute for the incoming instances
            
            // 1st column of the transformation matrix
            GL20.glEnableVertexAttribArray(Shader.VsAttribLocInstances_Position_0);
            GL20.glVertexAttribPointer(Shader.VsAttribLocInstances_Position_0, 4, GL11.GL_FLOAT, false, Matrix4f.ByteSize, 0);
            GL33.glVertexAttribDivisor(Shader.VsAttribLocInstances_Position_0, 1);
            
            // 2nd column of the transformation matrix4x4
            GL20.glEnableVertexAttribArray(Shader.VsAttribLocInstances_Position_1);
            GL20.glVertexAttribPointer(Shader.VsAttribLocInstances_Position_1, 4, GL11.GL_FLOAT, false, Matrix4f.ByteSize, 4 * Float.BYTES);
            GL33.glVertexAttribDivisor(Shader.VsAttribLocInstances_Position_1, 1);
            
            // 3rd column of the transformation matrix4x4
            GL20.glEnableVertexAttribArray(Shader.VsAttribLocInstances_Position_2);
            GL20.glVertexAttribPointer(Shader.VsAttribLocInstances_Position_2, 4, GL11.GL_FLOAT, false, Matrix4f.ByteSize, 8 * Float.BYTES);
            GL33.glVertexAttribDivisor(Shader.VsAttribLocInstances_Position_2, 1);
            
            // 4th column of the transformation matrix4x4
            GL20.glEnableVertexAttribArray(Shader.VsAttribLocInstances_Position_3);
            GL20.glVertexAttribPointer(Shader.VsAttribLocInstances_Position_3, 4, GL11.GL_FLOAT, false, Matrix4f.ByteSize, 12 * Float.BYTES);
            GL33.glVertexAttribDivisor(Shader.VsAttribLocInstances_Position_3, 1);
        }
    }

    /**
     * If this shader supports instanced rendering.
     */
    public boolean isInstanced() {
        return _isInstanced;
    }

    /**
     * If this shader supports colored rendering.
     */
    public boolean isColored() {
        return _isColored;
    }

    /**
     * If this shader supports textured rendering.
     */
    public boolean isTextured() {
        return _isTextured;
    }

    /**
     * If this shader supports instanced rendering, the maximum amount of rendered instances.
     */
    public int getMaxInstances() {
        return _numInstances;
    }

    /**
     * If this shader supports textured rendering, the location of the texture unit for the first texture.
     */
    public int getTexture0Location() {
        return _locTexture0;
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
        Shader other = (Shader) obj;
        if (_id != other._id)
            return false;
        return true;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              PRIVATE                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private final int _id; 
    
    // Uniform locations
    int _locWVP          = -1;
    int _locTexture0     = -1;
    
    boolean _isInstanced  = false;
    boolean _isColored    = false;
    boolean _isTextured   = false;
    int     _numInstances = 0;

}
