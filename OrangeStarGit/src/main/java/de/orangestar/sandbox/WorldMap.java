package de.orangestar.sandbox;

import de.orangestar.engine.logic.GameObject;
import de.orangestar.engine.logic.modules.RenderModule;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.actor.TileMap;
import de.orangestar.engine.render.actor.ModernTileMap;
import de.orangestar.engine.render.actor.ModernTileMap.Surface;
import de.orangestar.engine.resources.ResourceManager;
import de.orangestar.engine.tools.random.NoiseGenerator;
import de.orangestar.engine.tools.random.PerlinNoiseGenerator;
import de.orangestar.engine.values.Vector3f;

public class WorldMap extends GameObject {
    
    private static class WorldMapRenderModule extends RenderModule {
        public WorldMapRenderModule(GameObject parent) {
            super(parent);
            
            double[][] noise = new PerlinNoiseGenerator().generate2DMap(160, 120, System.currentTimeMillis());
            noise = NoiseGenerator.translate(noise, -1.0d, +1.0d, 0.0d, 1.0d);
            
            // 1.0-0.6: GRASS, 0.6-0.4: GRASS/DIRT, 0.4-0.0: DIRT 
            int data[][] = new int[noise.length][noise[0].length];
            for(int i = 0; i < data.length; i++) {
                for(int p = 0; p < data[0].length; p++) {
                    double val = noise[i][p];
                    if (val > 0.6d) {
                        data[i][p] = 10;
                    } 
                    else if (val > 0.4d) {
                        data[i][p] = 41;
                    } else {
                        data[i][p] = 14;
                    }
                }
            }

            Texture tileset = ResourceManager.Get().getTexture("textures/texture_large.png");
            TileMap tilemap = new TileMap(tileset, 16, 16);
            tilemap.setData(data);
//          double[][] noise = new PerlinNoiseGenerator().generate2DMap(40, 40, System.currentTimeMillis());
//          noise = NoiseGenerator.translate(noise, -1.0d, +1.0d, 0.0d, 1.0d);
//          
//          Surface dirt = new Surface("dirt")
//                                  .tiles(0, 1, 2, 3, 8, 9, 10, 11)
//                                  .nonsolid();
//          
//          Surface grass = new Surface("grass")
//                                  .tiles(4, 5, 6, 7, 12, 13, 14, 15)
//                                  .nonsolid();
//          
//          // 1.0-0.6: GRASS, 0.6-0.4: GRASS/DIRT, 0.4-0.0: DIRT             
//          Surface[][] data = new Surface[noise.length][noise[0].length];
//          for(int i = 0; i < data.length; i++) {
//              for(int p = 0; p < data[0].length; p++) {
//                  double val = noise[i][p];
//                  if (val > 0.6d) {
//                      data[i][p] = grass;
//                  } 
//                  else if (val > 0.4d) {
//                      data[i][p] = grass;
//                  } else {
//                      data[i][p] = dirt;
//                  }
//              }
//          }
//          
//          Texture tileset = ResourceManager.Get().getTexture("textures/texture_new.png");
//          ModernTileMap tilemap = new ModernTileMap(tileset, 16, 16);
//          tilemap.setData(data);            
            setRootActor(tilemap);
        }

    }
    
    public WorldMap() {        
        _moduleRender = new WorldMapRenderModule(this);
        getTransform().scale = new Vector3f( 1f, 1f, 0f);
    }
    
}
