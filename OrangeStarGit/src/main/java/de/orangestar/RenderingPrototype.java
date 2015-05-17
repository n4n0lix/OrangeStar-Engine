package de.orangestar;
  
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
  
public class RenderingPrototype {
  
    private static class Vector3f {
        public static int ByteSize = 3 * Float.BYTES;
        
        public float x,y,z;
        
        public Vector3f(float x, float y, float z) {
            this.x = x; this.y = y; this.z = z;
        }
    }
    
    private static class Color4f {
        public static int ByteSize = 4 * Float.BYTES;
        
        public float r,g,b,a;
        
        public Color4f(float r, float g, float b, float a ) {
            this.r = r; this.g = g; this.b = b; this.a = a;
        }
    }
    
    private static class Vertex {
        public static int ByteSize = Vector3f.ByteSize + Color4f.ByteSize;
        
        public Vector3f position;
        public Color4f  color;
        
        public Vertex(Vector3f position, Color4f  color) {
            this.position = position;
            this.color = color;
        }
    }
    
    
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
  
    // The window handle
    private long window;
  
    public void run() {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
  
        try {
            init();
            loop();
  
            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            errorCallback.release();
        }
    }
  
    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
  
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
  
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
  
        int WIDTH = 400;
        int HEIGHT = 300;
  
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
  
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });
  
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
  
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
  
        // Make the window visible
        glfwShowWindow(window);
    }
  
    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GLContext.createFromCurrent();
  
        // Set the clear color
        glClearColor(0.2f, 0.8f, 0.5f, 0.0f);
  
        createShader();
        createBuffer();
        fillBuffer();
  
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
  
            // Bind shader
            GL20.glUseProgram(shader);
            
            // Bind VAO
            GL30.glBindVertexArray( vao );
            
            offset = offset + 0.01f;
            GL20.glUniform3f(unilocation, offset, offset -1f , 1.0f);
            if(offset > 4.2f){
            	offset = 0;
            }
            
            // Do the draw call
            GL11.glDrawArrays( GL11.GL_TRIANGLES, 0, 3 );
            // Unbind vao
            GL30.glBindVertexArray( 0 );
            
            // Unbind shader
            GL20.glUseProgram(0);
            
            glfwSwapBuffers(window); // Display the rendering
  
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
  
    public static void main(String[] args) {
        new RenderingPrototype().run();
    }
    
    private void createBuffer() {
        
        vao = GL30.glGenVertexArrays(); // Generate a VertexArrayObject id (VAO wraps multiple VBO (VertexBufferObjects))
        GL30.glBindVertexArray(vao); // Set the VAO as the current vao target
        
            vbo = GL15.glGenBuffers(); // Generate a buffer id, which we will use as a VBO (VertexBufferObject) that contains the vertices
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo); // Set the VBO as the current buffer target
            
            // Set the buffer size to 1024*Vertex.ByteSize and initialize it with null values
            GL15.glBufferData( GL15.GL_ARRAY_BUFFER, 1024 * Vertex.ByteSize, null, GL15.GL_STREAM_DRAW );
                
            GL20.glEnableVertexAttribArray(0);  // Enable the first shader attribute 'position' (Vector3f with 3 floats)
            GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, Vertex.ByteSize, 0); // stride: The offset to the next 'position'attribute
    
            GL20.glEnableVertexAttribArray(1); // Enable the second shader attribute 'color' (Color4f with 4 floats)
            GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, Vertex.ByteSize, Vector3f.ByteSize); // pointerOffset: the offset to the first attribute of the current vertex
                        
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0); // Unbind the current target buffer
            
        GL30.glBindVertexArray(0); // Unbind the current target vao
    }
    
    private void fillBuffer() {
        Vertex[] vertices = new Vertex[] {
                new Vertex(new Vector3f(-1.0f, -1.0f, 0.0f), new Color4f(0.2f, 0f, 0.1f, 1f)),
                new Vertex(new Vector3f( 1.0f, -1.0f, 0.0f), new Color4f(0.4f, 0.2f, 0.6f, 1f)),
                new Vertex(new Vector3f( 0.0f,  1.0f, 0.0f), new Color4f(0.7f, 0.8f, 0.4f, 1f)),
        };
        
        // 1# Write the vertex data into a java buffer
        FloatBuffer buffer = BufferUtils.createByteBuffer(3 * Vertex.ByteSize).asFloatBuffer();
        buffer.position(0); // Set the current buffer write position to 0
        for(Vertex vertex : vertices) {
            buffer.put(vertex.position.x);
            buffer.put(vertex.position.y);
            buffer.put(vertex.position.z);
            buffer.put(vertex.color.r);
            buffer.put(vertex.color.g);
            buffer.put(vertex.color.b);
            buffer.put(vertex.color.a);
        }
        buffer.flip(); // flip the buffer, THIS IS VERY IMPORTANT
        
        // 2# Write data
        GL30.glBindVertexArray(vao);
        
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo);
            GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer); // Fill the vertexbuffer with the content of the java buffer (send the vertex data to OpenGL)
            GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, 0);
            
        GL30.glBindVertexArray(0);
    }
    
    private void createShader() {
        String dummyVertexShaderSrc =
                "#version 330 core"
                + "\n" + "uniform vec3 uni_wvp;"
                + "\n" + "layout(location = 0) in vec3 vs_position;"
                + "\n" + "layout(location = 1) in vec4 vs_color;"
                + "\n" + ""
                + "\n" + "out vec4 fs_color;"
                + "\n" + ""
                + "\n" + "void main() {"
                + "\n" + "    gl_Position = vec4(vs_position, 1.0) * vec4(uni_wvp, 1.0);"
                + "\n" + "    fs_color = vs_color;"
                + "\n" + "}"
                ;
        
        String dummyFragmentShaderSrc = 
                "#version 330 core"
                + "\n" + "in vec4 fs_color;"
                + "\n" + ""
                + "\n" + "out vec4 out_color;"
                + "\n" + ""
                + "\n" + "void main() {"
                + "\n" + "    out_color = fs_color;"
                + "\n" + "}";
        
        System.out.println("Vertex-Shader: \n" + dummyVertexShaderSrc + "\n");
        System.out.println("Fragment-Shader: \n" + dummyFragmentShaderSrc + "\n");
        
        // 1# Read/Compile VertexShader
        int idVertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(idVertexShader, dummyVertexShaderSrc);
        GL20.glCompileShader(idVertexShader);
  
        if (GL20.glGetShaderi(idVertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile vertex shader: " + GL20.glGetShaderInfoLog(idVertexShader));
            System.exit(-1);
        }
        
        // 2# Read/Compile FragmentShader
        int idFragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(idFragmentShader, dummyFragmentShaderSrc);
        GL20.glCompileShader(idFragmentShader);
  
        if (GL20.glGetShaderi(idFragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile fragment shader: " + GL20.glGetShaderInfoLog(idFragmentShader));
            System.exit(-1);
        }
        
        // 3# Create Shader-Program
        shader = GL20.glCreateProgram();
        GL20.glAttachShader(shader, idVertexShader);
        GL20.glAttachShader(shader, idFragmentShader);
        
        GL20.glBindAttribLocation(shader, 0, "vs_position");
        GL20.glBindAttribLocation(shader, 1, "vs_color");
  
        GL20.glLinkProgram(shader);
        if (GL20.glGetProgrami(shader, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.out.println("Shader linking failed: " + GL20.glGetProgramInfoLog(shader));
            System.exit(-1);
        }
        unilocation = GL20.glGetUniformLocation(shader, "uni_wvp");
  
        GL20.glValidateProgram(shader);
        GL20.glDeleteShader(idVertexShader);
        GL20.glDeleteShader(idFragmentShader);
    }
    
    private float offset;
    private int unilocation;
    private int vao;
    private int vbo;
    private int shader;
}