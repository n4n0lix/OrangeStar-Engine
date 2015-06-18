package de.orangestar.game;

import java.util.LinkedList;
import java.util.List;

import de.orangestar.engine.World;
import de.orangestar.engine.input.InputManager;
import de.orangestar.engine.input.Keyboard;
import de.orangestar.engine.input.Mouse;
import de.orangestar.engine.logic.GameManager;
import de.orangestar.engine.logic.GameState;
import de.orangestar.engine.render.GLWindow;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.render.camera.OrthographicCamera;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.game.gameobjects.Player;
import de.orangestar.game.gameobjects.map.Map;

public class MainGameStateDummy extends GameState {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public static float ZOOM_SPEED   = 4f;
    public static float SCROLL_SPEED = 256f;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    @Override
    public void onStateStart() {

        // Setup World
        World.Get().addGameObject(map);
        for(int i = 0; i < 200; i++) {
            Player player = new Player();
            map.addChild(player);
            World.Get().addGameObject(player);
        }
        
        // Setup Rendering
        RenderManager render = RenderManager.Get();
        
        render.setActiveCamera(camera);
        
        float width_2  = render.getMainWindow().getRenderWidth() * 2;
        float height_2 = render.getMainWindow().getRenderHeight() * 2;
        render.setProjectionMatrix(Matrix4f.ortho2D( -width_2 , width_2 , height_2, -height_2)); // Setup basic 2D orthographical view
    }

    @Override
    public void onUpdate() {
        handleCamera();
        handleScrolling();
        handleZooming();
    }

    @Override
    public void onStateEnd() {

    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private void handleCamera() {
        GLWindow window = RenderManager.Get().getMainWindow();
        
        float width_2 = window.getWidth() * 0.5f;
        float height_2 = window.getHeight() * 0.5f;
        
        camera.setViewport(-width_2, -height_2, width_2, height_2);
    }
    
    private void handleScrolling() {
        Keyboard keyboard = InputManager.Get().getKeyboard();
        float speed = SCROLL_SPEED;
        
        if (keyboard.W.isDown()) {
            camera.getEye().y -= SCROLL_SPEED * GameManager.DELTA_TIME;
        }
        
        if (keyboard.S.isDown()) {
            camera.getEye().y += SCROLL_SPEED * GameManager.DELTA_TIME;
        }
        
        if (keyboard.A.isDown()) {
            camera.getEye().x -= SCROLL_SPEED * GameManager.DELTA_TIME;
        }
        
        if (keyboard.D.isDown()) {
            camera.getEye().x += SCROLL_SPEED * GameManager.DELTA_TIME;
        }
    }
    
    private void handleZooming() {
        Mouse mouse = InputManager.Get().getMouse();

        _zoom *= 1f + -mouse.getScrollOffset() * ZOOM_SPEED * GameManager.DELTA_TIME;
        
        if (_zoom < 0.25f) {
            _zoom = 0.25f;
        }
        
        if (_zoom > 2f) {
            _zoom = 2f;
        }
        
        // Apply Zoom
        GLWindow window = RenderManager.Get().getMainWindow();
        
        float width  = window.getRenderWidth()  * _zoom;
        float height = window.getRenderHeight() * _zoom;
        float width_2  = width/2;
        float height_2 = height/2;
        
        camera.setViewport( -width_2, -height_2, width_2, height_2 );
    }
    
    private Map                map    = new Map();

    private OrthographicCamera camera = new OrthographicCamera();
    
    private float _zoom             = 1f;

}
