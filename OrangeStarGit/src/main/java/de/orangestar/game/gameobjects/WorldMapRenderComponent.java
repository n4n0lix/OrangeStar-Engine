package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.render.GLWindow;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.actor.ModernTileMap;
import de.orangestar.engine.render.actor.TileMap;
import de.orangestar.engine.render.actor.ModernTileMap;
import de.orangestar.engine.render.actor.ModernTileMap.Surface;
import de.orangestar.engine.render.component.UnitRenderComponent;
import de.orangestar.engine.tools.random.NoiseGenerator;
import de.orangestar.engine.tools.random.PerlinNoiseGenerator;
import de.orangestar.engine.values.Vector3f;

public class WorldMapRenderComponent extends UnitRenderComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public WorldMapRenderComponent(GameObject obj) {
        super(obj);
        
        _window = RenderManager.Get().getMainWindow();
        _renderWidth = _window.getRenderWidth();
        _renderHeight = _window.getRenderHeight();

        _actorGround = new ModernTileMap(new Texture("textures/WorldTileSetDummy_16x16.png"), 16, 16);
        
        setLayer(0);
        updateSize();
        setActor(_actorGround);
    }

    public void onRender() {
        // Check if render window has changed in size
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
        Vector3f unitScale = getGameObject().getLocalTransform().scale;
        
        float scaleX = unitScale.x;
        float scaleY = unitScale.y;
        
        int mapWidth =  (int)(_renderWidth  / scaleX / _actorGround.getTileWidth()) + 1;
        int mapHeight = (int)(_renderHeight / scaleY / _actorGround.getTileHeight()) + 1;

        int[][] result = new int[mapWidth+1][mapHeight+1];
        result[1][1] = 5;

        _actorGround.setData(generateTestData());
    }

    private GLWindow _window;
    private ModernTileMap  _actorGround;
    private int      _renderWidth, _renderHeight;

    private static Surface[][] generateTestData() {
        Surface watr = new Surface();
        watr.setLayer(0);
        watr.setSolid(false);
        watr.addTileIds( 16, 17, 18, 19, 24, 25, 26, 27 );
        
        Surface dirt = new Surface();
        dirt.setLayer(1);
        dirt.setSolid(false);
        dirt.addTileIds( 0, 1, 2, 3, 8, 9, 10, 11);
        
        Surface gras = new Surface();
        gras.setLayer(2);
        gras.setSolid(false);
        gras.addTileIds( 4, 5, 6, 7, 12, 13, 14, 15);

//        Surface[][] data = new Surface[][] {
//                new Surface[] { gras, gras, gras, gras, gras, gras, gras, gras},
//                new Surface[] { gras, dirt, dirt, dirt, dirt, gras, gras, gras},
//                new Surface[] { gras, dirt, watr, watr, dirt, gras, gras, gras},
//                new Surface[] { gras, dirt, watr, watr, dirt, gras, gras, gras},
//                new Surface[] { gras, dirt, watr, watr, dirt, gras, gras, gras},
//                new Surface[] { gras, dirt, watr, watr, dirt, gras, gras, gras},
//                new Surface[] { gras, dirt, dirt, dirt, dirt, gras, gras, gras},
//                new Surface[] { gras, gras, gras, gras, gras, gras, gras, gras},                
//        };
        
        double[][] randomNoise = NoiseGenerator.translate(new PerlinNoiseGenerator().generate2DMap(64, 32), -1d, 1d, 0d, 3d);
        
        
        Surface[][] data = new Surface[48][32];
        for(int x = 0; x < data.length; x++) {
            for(int y = 0; y < data[0].length; y++) {
                if (randomNoise[x][y] < 1.3d) {
                    data[x][y] = watr;
                } else if(randomNoise[x][y] < 1.6d) {
                    data[x][y] = dirt;
                } else {
                    data[x][y] = gras;
                }
            }
        }
        
        
        return data;
    }
    
}
