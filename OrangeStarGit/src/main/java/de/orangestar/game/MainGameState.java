package de.orangestar.game;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.GameState;
import de.orangestar.engine.World;
import de.orangestar.engine.input.InputEngine;
import de.orangestar.engine.input.Key.KeyState;
import de.orangestar.engine.input.Keyboard;
import de.orangestar.engine.input.Mouse;
import de.orangestar.engine.logic.LogicEngine;
import de.orangestar.engine.physics.PhysicsEngine;
import de.orangestar.engine.render.GLWindow;
import de.orangestar.engine.render.OrthographicCamera;
import de.orangestar.engine.render.RenderEngine;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.actor.ui.Font;
import de.orangestar.engine.utils.MathUtils;
import de.orangestar.engine.utils.Pair;
import de.orangestar.engine.values.Rectangle4i;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Viewport4f;
import de.orangestar.game.gameobjects.item.ItemModel;
import de.orangestar.game.gameobjects.item.ItemModelLogicComponent.ItemSpawner;
import de.orangestar.game.gameobjects.item.prototypes.Knife;
import de.orangestar.game.gameobjects.item.prototypes.Sword;
import de.orangestar.game.gameobjects.map.Map;
import de.orangestar.game.gameobjects.map.MapLogicComponent;
import de.orangestar.game.gameobjects.map.surfaces.Grass;
import de.orangestar.game.gameobjects.player.PlayableGroup;
import de.orangestar.game.gameobjects.player.Player;
import de.orangestar.game.gameobjects.player.action.Action;
import de.orangestar.game.gameobjects.player.action.ai.BuildAIAction;
import de.orangestar.game.gameobjects.player.action.ai.ChopAIAction;
import de.orangestar.game.gameobjects.props.PropsModel;
import de.orangestar.game.gameobjects.tree.TreeFlyweight;
import de.orangestar.game.gameobjects.tree.TreeModel;
import de.orangestar.game.gameobjects.ui.UI;

/**
 * The main gamestate.
 * 
 * @author Basti
 */
public class MainGameState extends GameState {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public static final int CAMERA_LAYER_UI        = 1;
    public static final int CAMERA_LAYER_GAMEWORLD = 0;
    
    public static final int LAYER_WORLD         = 0;
    public static final int LAYER_PROPS         = 1;
    public static final int LAYER_ITEM		    = 2;
    public static final int LAYER_PLAYER        = 3;
    public static final int LAYER_VEGETATION    = 4;

    public static final float ZOOM_MIN          = 0.1f;
    public static final float ZOOM_INIT         = 0.4f;
    public static final float ZOOM_MAX          = 2f;

    public static final float ZOOM_SENSITIVITY  = 0.025f; // 0.005 to 0.1
    public static final float ZOOM_SPEED        = 0.05f; // 0.01 to 0.1
    
    public static final float SCROLL_SPEED      = 256f;

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
 
    /**
     * Spawns a new player in the world.
     * @param x The x coordinate (world position)
     * @param y The y coordinate (world position)
     * @return The spawned player
     */
    public Player spawnNewPlayer(float x, float y) {
        Player player = new Player();
        player.getPhysicsComponent().teleport(x, y, 0.0f);
        
        _group.addPlayer(player);
        
        GAME_WORLD.addGameObject(player);
        
        return player;
    }
    
    @Override
    public void onFrameStart() {
        _uiCamera.setViewport(0, 0, getRenderEngine().getRenderWidth(), getRenderEngine().getRenderHeight());
        
        handleClicking();
        handleScrolling();
        doZooming();
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Protected                             */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    @Override
    protected void onStateStart() {
        initUIWorld();
        initGameWorld();
    }

    @Override
    protected void onStateUpdate() {
        _group.doActions();
        calcZooming();
    }

    @Override
    protected void onStateEnd() {
        GAME_WORLD.destroyAndClear();
        _uiWorld.destroyAndClear();
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private void handleClicking() {
    	Mouse mouse = getInputEngine().getMouse();
    	
    	if (mouse.LEFT.isPressed()) {
    	    onClickChopTree( (float) mouse.getX(), (float) mouse.getY() );
    	}
    	
        if (mouse.RIGHT.isPressed()) {
            onClickChangeSurfaceToGrass( (float)mouse.getX(), (float)mouse.getY() );
        }
    }
    
    private void onClickChopTree( float clickX, float clickY ) {
        Vector3f pos = _gameCamera.mouseScreenToWorld(clickX, clickY);
        
        List<GameObject> objects = getPhysicsEngine().getGameObjectsByPoint(GAME_WORLD, pos);
        Collections.sort(objects, new Comparator<GameObject>() {
                                            @Override
                                            public int compare(GameObject o1, GameObject o2) {
                                                return Float.compare(o2.getGlobalTransform().position.y, o1.getGlobalTransform().position.y);
                                            }
                                        });
        
        if (!objects.isEmpty() && objects.get(0) instanceof TreeFlyweight) {
            TreeFlyweight tree = (TreeFlyweight) objects.get(0);
            _group.addActions(new ChopAIAction(tree));
        }
    }
    
    private void onClickChangeSurfaceToGrass( float clickX, float clickY ) {
        Vector3f click = _gameCamera.mouseScreenToWorld( clickX, clickY  );

        Pair<Integer, Integer> tile = MapLogicComponent.getTileIndexAt(click.x, click.y);

        Action action = new BuildAIAction(tile.x, tile.y, Grass.Instance);
        _group.addActions(action);
    }

    private void handleScrolling() {
        Keyboard keyboard = getInputEngine().getKeyboard();
        Vector3f newEye = Vector3f.POOL.get();
        Vector3f.set(newEye, _gameCamera.getPosition());

        // Calculate scrolling factor
        _scrollingFactor = MathUtils.translate(_zoomIs, ZOOM_MIN, ZOOM_MAX, 0.5f, 2f);
        
        if (keyboard.W.isHoldDown()) {
            newEye.y -= SCROLL_SPEED * _scrollingFactor * LogicEngine.DELTA_TIME;
        }
        
        if (keyboard.S.isHoldDown()) {
            newEye.y += SCROLL_SPEED * _scrollingFactor * LogicEngine.DELTA_TIME;
        }
        
        if (keyboard.A.isHoldDown()) {
            newEye.x -= SCROLL_SPEED * _scrollingFactor * LogicEngine.DELTA_TIME;
        }
        
        if (keyboard.D.isHoldDown()) {
            newEye.x += SCROLL_SPEED * _scrollingFactor * LogicEngine.DELTA_TIME;
        }
        
        if (!newEye.equals(_gameCamera.getPosition())) {
            _gameCamera.setPosition(newEye);
        }
        
        Vector3f.POOL.release(newEye);
    }
    
    private void calcZooming() {
        Mouse mouse = getInputEngine().getMouse();
        
        float scrollY = (float) -mouse.getScrollOffset();

        // Calculate zoom target
        _zoomTarget += scrollY * ZOOM_SENSITIVITY;
        _zoomTarget = Math.max(ZOOM_MIN, Math.min( ZOOM_MAX, _zoomTarget));
    }
    
    private void doZooming() {
        // Calculate zoom
        _zoomIs = MathUtils.lerp(_zoomIs, _zoomTarget, ZOOM_SPEED);
        
        // Apply Zoom        
        float width  = getRenderEngine().getRenderWidth()  * _zoomIs;
        float height = getRenderEngine().getRenderHeight() * _zoomIs;
        float width_2  = width * 0.5f;
        float height_2 = height * 0.5f;
        
        Viewport4f camViewport = _gameCamera.getViewport();
        Viewport4f newViewport = new Viewport4f(-width_2, -height_2, width_2, height_2);

        if (!camViewport.equals(newViewport)) {
            _gameCamera.setViewport(newViewport);
        }
    }
    
    private void initGameWorld() {
        GAME_WORLD = new World();
        
        _gameCamera = new OrthographicCamera();
        _gameCamera.setLayer(CAMERA_LAYER_GAMEWORLD);
        _gameCamera.setWorld(GAME_WORLD);
        _gameCamera.setPosition(new Vector3f(100, 100));
        getRenderEngine().addActiveCamera(_gameCamera);
        
        // # Map
        _map = new Map();
        GAME_WORLD.addGameObject(_map);
        
        // # Tree Model
        _treeModel = new TreeModel();
        GAME_WORLD.addGameObject(_treeModel);
        
        // # Item Model
        _itemModel = new ItemModel();
        GAME_WORLD.addGameObject(_itemModel);
        
        // # Props Model
        _propsModel = new PropsModel();
        GAME_WORLD.addGameObject(_propsModel);
        
        // Setup test content
        spawnNewPlayer( 0,            0 );
        spawnNewPlayer( 1*8*16,       0 );
        spawnNewPlayer( 0,       1*8*16 );
        spawnNewPlayer( 1*8*16,  1*8*16 );

        ItemSpawner swordSpawner = _itemModel.getLogicComponent().getItemSpawner(Sword.Instance);
        swordSpawner.spawnItem(new Vector3f(120f, 190f));
        
        ItemSpawner knifeSpawner = _itemModel.getLogicComponent().getItemSpawner(Knife.Instance);
        knifeSpawner.spawnItem(new Vector3f(240f, 290f));
    }
    
    private void initUIWorld() {
        _uiWorld = new World();
        
        _uiCamera = new OrthographicCamera();
        _uiCamera.setWorld(_uiWorld);
        _uiCamera.setLayer(CAMERA_LAYER_UI);
        
        getRenderEngine().addActiveCamera(_uiCamera);
        
        _uiWorld.addGameObject(new UI());
    }
    
    public static World        GAME_WORLD;
    private OrthographicCamera _gameCamera;
    private float              _zoomTarget = ZOOM_INIT;
    private float              _zoomIs     = _zoomTarget;
    private float              _scrollingFactor = 0.0f;

    public World               _uiWorld;
    private OrthographicCamera _uiCamera;
    
    private Map                _map;
    private TreeModel          _treeModel;
    private ItemModel		   _itemModel;
    private PropsModel         _propsModel;
    
    private PlayableGroup      _group  = new PlayableGroup();
}
