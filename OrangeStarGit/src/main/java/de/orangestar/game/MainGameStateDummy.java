package de.orangestar.game;

import de.orangestar.engine.World;
import de.orangestar.engine.input.InputManager;
import de.orangestar.engine.input.Keyboard;
import de.orangestar.engine.input.Mouse;
import de.orangestar.engine.logic.GameManager;
import de.orangestar.engine.logic.GameState;
import de.orangestar.engine.physics.component.UnitPhysicsComponent;
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
    	map.addChild(player);
    	
        World.Get().addGameObject(player);
        
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
        
        // MOVE MAP
        if (keyboard.ShiftLeft.isDown()) {
            speed *= 4f;
        }
        
        if (keyboard.W.isDown()) {
            map.getPhysicsModule()._velocityY += speed;
        }
        
        if (keyboard.S.isDown()) {
            map.getPhysicsModule()._velocityY -= speed;
        }
        
        if (keyboard.A.isDown()) {
            map.getPhysicsModule()._velocityX += speed;
        }
        
        if (keyboard.D.isDown()) {
            map.getPhysicsModule()._velocityX -= speed;
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
        
        camera.setViewport(-width_2 , -height_2, width_2, height_2);
    }
    
    private Player             player = new Player();
    private Map                map    = new Map();

    private OrthographicCamera camera = new OrthographicCamera();
    
    private float _zoom             = 1f;

}
