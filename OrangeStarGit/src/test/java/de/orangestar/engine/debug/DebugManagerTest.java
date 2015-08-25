//package de.orangestar.engine.debug;
//
//import static org.lwjgl.glfw.GLFW.glfwInit;
//
//import java.io.File;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.lwjgl.opengl.GL11;
//
//import de.orangestar.engine.render.RenderManager;
//
//public class DebugManagerTest {
//
//    @Before
//    public void setup() {
//        System.out.println(new File("libs").getAbsolutePath());
//        System.setProperty("org.lwjgl.librarypath", new File("libs").getAbsolutePath());
//        if ( glfwInit() != GL11.GL_TRUE ) {
//            throw new IllegalStateException("Unable to initialize GLFW");
//        }
//        
//        RenderManager.Get().onStart();
//    }
//    
//    @Test
//    public void lifecycle() {
//        // Just test that no exceptions are thrown
//        DebugManager.Get().onStart();
//        DebugManager.Get().onUpdate();
//        DebugManager.Get().onShutdown();
//    }
//    
//    @After
//    public void cleanup() {
//        RenderManager.Get().onShutdown();
//    }
//}
