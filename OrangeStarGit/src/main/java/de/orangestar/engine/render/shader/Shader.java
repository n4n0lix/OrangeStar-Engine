package de.orangestar.engine.render.shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;

import de.orangestar.engine.debug.Logger;
import de.orangestar.engine.render.IRenderEngine;
import de.orangestar.engine.render.RenderEngine;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Vector2f;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Vertex;

/**
 * A shader defines how vertices are being displayed by OpenGL (position, color, ...).
 * Instances are created via {@link ShaderBuilder}.
 * 
 * @author Oliver &amp; Basti
 */
public class Shader {

    /* Shader layout */
    /* Vertex shader - uniforms */
    public static final String UniAttribNameWVP         = "uni_wvp";
    public static final String UniAttribNameTexture0    = "uni_texture0";
    public static final String UniAttribNameInstanced   = "uni_is_instanced";
    
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
    public static final Shader GodShader = new ShaderBuilder()
                                                            .textured()
                                                            .colored()
                                                            .build();
    
    public static final Shader ColorShader = new ShaderBuilder()
                                                            .colored()
                                                            .build();

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               PUBLIC                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public void updateWVP(IRenderEngine engine) {       
        GL20.glUniformMatrix4fv(_uniLocationWVP, 1, true, engine.getWVPBuffer());
    }
    
    /**
     * Set this shader as the active shader.
     */
    public void bind() {
        // Prevent multiple binding of the same shader to improve performance
        if (CURRENT_PROGRAM_ID != _id) {
            CURRENT_PROGRAM_ID = _id;
            GL20.glUseProgram(_id);
        }
    }
    
    /**
     * Set if instanced rendering is enabled.
     * @param instanced if instanced rendering is enabled
     */
    public void setInstanced(boolean instanced) {
        if (_uniInstanced == instanced) {
            return;
        }
        
        bind();        
        _uniInstanced = instanced;
        GL20.glUniform1i(_uniLocationInstanced, _uniInstanced ? 1 : 0);
    }
    
    /**
     * Tells OpenGL the vertex layout of this shader. Use this when a VBO is setup.
     */
    public void layoutVBO() {
        int offset = 0;
        
        // Setup vertex position attribute
        GL20.glEnableVertexAttribArray(Shader.VsAttribLocPosition);
        GL20.glVertexAttribPointer(Shader.VsAttribLocPosition, Vector3f.NUM_COMPONENTS, GL11.GL_FLOAT, false, Vertex.BYTE_SIZE, offset); 
        offset += Vector3f.BYTE_SIZE;

        // Setup vertex color attribute
        if (_isColored) {
            GL20.glEnableVertexAttribArray(Shader.VsAttribLocColor);
            GL20.glVertexAttribPointer(Shader.VsAttribLocColor, Color4f.NUM_COMPONENTS, GL11.GL_FLOAT, false, Vertex.BYTE_SIZE, offset); 
            offset += Color4f.BYTE_SIZE;
        }
        
        // Setup vertex texcoord attribute
        if (_isTextured) {
            GL20.glEnableVertexAttribArray(Shader.VsAttribLocTexCoord);
            GL20.glVertexAttribPointer(Shader.VsAttribLocTexCoord, Vector2f.NUM_COMPONENTS, GL11.GL_FLOAT, false, Vertex.BYTE_SIZE, offset); 
            offset += Vector2f.BYTE_SIZE;
        }
    }
    
    /**
     * Tells OpenGL the instances layout of this shader. Use this when a BufferObject for instanced rendering is setup.
     */
    public void layoutInstancedRendering() {
        // Setup the transform attribute for the incoming instances
        
        // 1st column of the transformation matrix
        GL20.glEnableVertexAttribArray(Shader.VsAttribLocInstances_Position_0);
        GL20.glVertexAttribPointer(Shader.VsAttribLocInstances_Position_0, 4, GL11.GL_FLOAT, false, Matrix4f.BYTE_SIZE, 0);
        GL33.glVertexAttribDivisor(Shader.VsAttribLocInstances_Position_0, 1);
        
        // 2nd column of the transformation matrix4x4
        GL20.glEnableVertexAttribArray(Shader.VsAttribLocInstances_Position_1);
        GL20.glVertexAttribPointer(Shader.VsAttribLocInstances_Position_1, 4, GL11.GL_FLOAT, false, Matrix4f.BYTE_SIZE, 4 * Float.BYTES);
        GL33.glVertexAttribDivisor(Shader.VsAttribLocInstances_Position_1, 1);
        
        // 3rd column of the transformation matrix4x4
        GL20.glEnableVertexAttribArray(Shader.VsAttribLocInstances_Position_2);
        GL20.glVertexAttribPointer(Shader.VsAttribLocInstances_Position_2, 4, GL11.GL_FLOAT, false, Matrix4f.BYTE_SIZE, 8 * Float.BYTES);
        GL33.glVertexAttribDivisor(Shader.VsAttribLocInstances_Position_2, 1);
        
        // 4th column of the transformation matrix4x4
        GL20.glEnableVertexAttribArray(Shader.VsAttribLocInstances_Position_3);
        GL20.glVertexAttribPointer(Shader.VsAttribLocInstances_Position_3, 4, GL11.GL_FLOAT, false, Matrix4f.BYTE_SIZE, 12 * Float.BYTES);
        GL33.glVertexAttribDivisor(Shader.VsAttribLocInstances_Position_3, 1);
    }

    /**
     * If this shader supports colored rendering.
     * @return If this shader supports colored rendering
     */
    public boolean isColored() {
        return _isColored;
    }

    /**
     * If this shader supports textured rendering.
     * @return If this shader supports textured rendering
     */
    public boolean isTextured() {
        return _isTextured;
    }

    /**
     * If this shader supports textured rendering, the location of the texture unit for the first texture.
     * @return The location of the texture unit for the first texture
     */
    public int getTexture0Location() {
        return _uniLocationTexture0;
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
    /*                              Package                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Package-Constructor.
     * @param vertex The vertex shader source code
     * @param fragment The fragment shader source code
     */
    Shader(String vertex, String fragment) {

//        DebugManager.Get().info(this.getClass(), "Vertex-Shader: \n" + vertex + "\n");
//        DebugManager.Get().info(this.getClass(), "Fragment-Shader: \n" + fragment + "\n");
        
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

        // 4# Get Shader Layout Attribute Locations
        _uniLocationWVP       = GL20.glGetUniformLocation(_id, Shader.UniAttribNameWVP);
        _uniLocationTexture0  = GL20.glGetUniformLocation(_id, Shader.UniAttribNameTexture0);
        _uniLocationInstanced = GL20.glGetUniformLocation(_id, Shader.UniAttribNameInstanced);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private final int _id; 
    
    // Uniform locations
    int _uniLocationWVP          = -1;
    int _uniLocationTexture0     = -1;
    int _uniLocationInstanced    = -1;
    
    boolean  _uniInstanced  = false;
    
    boolean _isColored    = false;
    boolean _isTextured   = false;
    int     _numInstances = 0;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private static int CURRENT_PROGRAM_ID = -1;
    
}
