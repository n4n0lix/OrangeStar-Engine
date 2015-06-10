package de.orangestar.game.gameobjects;

import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.render.GLWindow;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.actor.TileMap;
import de.orangestar.engine.render.component.UnitRenderComponent;
import de.orangestar.engine.values.Vector3f;

public class WorldMapRenderComponent extends UnitRenderComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public WorldMapRenderComponent(UnitLogicComponent logic) {
        super(logic);
        
        _window = RenderManager.Get().getMainWindow();
        _renderWidth = _window.getRenderWidth();
        _renderHeight = _window.getRenderHeight();

        _actorGround = new TileMap(new Texture("textures/WorldTileSetDummy_16x16.png"), 16, 16);
        
        setLayer(0);
        updateSize();
        setActor(_actorGround);
    }

    public void onRender() {
        if (_renderWidth != _window.getRenderWidth() || _renderHeight != _window.getRenderHeight()) {
            _renderWidth = _window.getRenderWidth();
            _renderHeight = _window.getRenderHeight();

            updateSize();
        }

        super.onRender();
    } 

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private void updateSize() {
        Vector3f unitScale = getUnitLogicComponent().getTransform().scale;
        
        float scaleX = unitScale.x;
        float scaleY = unitScale.y;
        
        int mapWidth =  (int)(_renderWidth  / scaleX / _actorGround.getTileWidth()) + 1;
        int mapHeight = (int)(_renderHeight / scaleY / _actorGround.getTileHeight()) + 1;
        
        System.out.println(mapWidth + "/" + mapHeight);
        
        _actorGround.setData(new int[mapWidth+1][mapHeight+1]);
    }

    private GLWindow _window;
    private TileMap  _actorGround;
    private int      _renderWidth, _renderHeight;

}
