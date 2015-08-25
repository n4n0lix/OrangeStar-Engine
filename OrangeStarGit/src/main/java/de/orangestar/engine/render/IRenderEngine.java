package de.orangestar.engine.render;

import java.nio.ByteBuffer;

import de.orangestar.engine.values.Matrix4f;

public interface IRenderEngine {
    
    public void onStart();
    
    public void onUpdate();
    
    public void onShutdown();
    
    public boolean requestsExit();
        
    public long getGLFWWindowHandle();
    
    public int getRenderWidth();
    
    public int getRenderHeight();
    
    public void addActiveCamera(Camera camera);
    
    public boolean isVSyncEnabled();
    
    public void setVSync(boolean vSync);
    
    public boolean isWireframeEnabled();
    
    public void setWireframe(boolean wireframe);
    
    public boolean isAlphablending();
    
    public void setAlphablending(boolean alphablending);
    
    public ByteBuffer getWVPBuffer();
    
    public Matrix4f getWorldMatrix();
    
    public void setWorldMatrix(Matrix4f matrix);
    
    public void setViewMatrix(Matrix4f matrix);
    
    public void setProjectionMatrix(Matrix4f matrix);
    
    public void setExtrapolation(float extrapolation);
    
    public float getExtrapolation();
}
