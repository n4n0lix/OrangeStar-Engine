package de.orangestar.sandbox;

import java.io.File;
import java.util.Random;

import de.orangestar.engine.logic.GameObject;
import de.orangestar.engine.logic.modules.RenderModule;
import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.actor.ClassicTileMap;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.resources.ResourceManager;
import de.orangestar.engine.tools.random.NoiseGenerator;
import de.orangestar.engine.tools.random.PerlinNoiseGenerator;

public class TestModuleRender extends RenderModule {

    public TestModuleRender(GameObject parent) {
        super(parent);
        
        NoiseGenerator gen = new PerlinNoiseGenerator();
        double[][] noise = gen.generate2DMap(160, 120, System.currentTimeMillis());
        noise = NoiseGenerator.translate(noise, -1.0d, +1.0d, 0.0d, 1.0d);
        
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
//                data[i][p] = (int) (noise[i][p]);
            }
        }
              
//        int[][] data = process(noise);


        ClassicTileMap tilemap;
        tilemap = new ClassicTileMap(ResourceManager.Get().getTexture("texture_large.png"), 16, 16);
//        tilemap = new ClassicTileMap(ResourceManager.Get().getTexture("greyscale.png"), 1, 1);
        tilemap.setData(data);
        
        setRootActor(tilemap);
    }

    @Override
    public void onRender() {
        
    }
    
    private int[][] process(double[][] input) {
        int width = input.length;
        int height = input[0].length;
        
        // 1# Determine what field is grass/dirt
        boolean[][] tmpResult = new boolean[width][height];
        
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                double val = input[x][y];
                
                if (val > 0.5d) {
                    tmpResult[x][y] = true; // GRASS
                } else {
                    tmpResult[x][y] = false; // DIRT
                }
            }
        }
        
        // 2# Determine the right tile to use per field
        int[][] result = new int[width][height];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                
                // False - Dirt    True - Grass
                boolean topleft = false, top = false, topright = false;
                boolean midleft = false, mid = false, midright = false;
                boolean botleft = false, bot = false, botright = false;
                
                
                mid = tmpResult[x][y];
                
                // Top
                if (inBounds(x-1,y-1,width,height)) {
                    topleft = tmpResult[x-1][y-1];
                } else { topleft = mid; }
                
                if (inBounds(x,y-1,width,height)) {
                    top = tmpResult[x][y-1];
                } else { top = mid; }
                
                if (inBounds(x+1,y-1,width,height)) {
                    topright = tmpResult[x+1][y-1];
                } else { topright = mid; }
                
                // Mid
                if (inBounds(x-1,y,width,height)) {
                    midleft = tmpResult[x-1][y];
                } else { midleft = mid; }
                
                if (inBounds(x+1,y,width,height)) {
                    midright = tmpResult[x+1][y];
                } else { midright = mid; }
                
                // Bot
                if (inBounds(x-1,y+1,width,height)) {
                    botleft = tmpResult[x-1][y+1];
                } else { botleft = mid; }
                
                if (inBounds(x,y+1,width,height)) {
                    bot = tmpResult[x][y+1];
                } else { bot = mid; }
                
                if (inBounds(x+1,y+1,width,height)) {
                    botright = tmpResult[x+1][y+1];
                }  else { botright = mid; }
                
                result[x][y] = 31; // Fill with empty tile per default
                
                // Determine what tile
                // PURE DIRT
                if(           !top 
                && !midleft && !mid && !midright
                           && !bot) {
                    result[x][y] = 14;
                }

                if(           top 
                && midleft && !mid && midright
                           && bot) {
                    result[x][y] = 14;
                }
                
                if(           top 
                && midleft && !mid && midright
                           && !bot) {
                    result[x][y] = 14;
                }
                
                if(           !top 
                && midleft && !mid && midright
                           && bot) {
                    result[x][y] = 14;
                }
                
                if(           top 
                && !midleft && !mid && midright
                           && bot) {
                    result[x][y] = 14;
                }
                
                if(           top 
                && midleft && !mid && !midright
                           && bot) {
                    result[x][y] = 14;
                }

                // PURE-GRASS
                if(           top 
                && midleft && mid && midright
                           && bot) {
                    result[x][y] = 10;
                }
                                
                if(           !top 
                && !midleft && mid && !midright
                           && !bot) {
                    result[x][y] = 10;
                }
                
                if(           !top 
                && midleft && mid && !midright
                           && !bot) {
                    result[x][y] = 10;
                }
                
                if(           !top 
                && !midleft && mid && midright
                           && !bot) {
                    result[x][y] = 10;
                }
                
                if(           top 
                && !midleft && mid && !midright
                           && !bot) {
                    result[x][y] = 10;
                }
                
                if(           !top 
                && !midleft && mid && !midright
                           && bot) {
                    result[x][y] = 10;
                }
                
                // HALF GRASS HALF DIRT
                if(          !top 
                && midleft && mid && midright
                           && bot) {
                    result[x][y] = 2;
                }
                
                if(           top 
                && midleft && mid && !midright
                           && bot) {
                    result[x][y] = 11;
                }
                
                if(           top 
                && !midleft && mid && midright
                           && bot) {
                    result[x][y] = 9;
                }
                
                if(           top 
                && midleft && mid && midright
                           && !bot) {
                    result[x][y] = 18;
                }
                
                // GRASS CORNERS
                if(          !top 
                && midleft && mid && !midright
                           && bot) {
                    result[x][y] = 3;
                }
                
                if(           !top 
                && !midleft && mid && midright
                           && bot) {
                    result[x][y] = 1;
                }
                
                if(           top 
                && !midleft && mid && midright
                           && !bot) {
                    result[x][y] = 17;
                }
                
                if(           top 
                && midleft && mid && !midright
                           && !bot) {
                    result[x][y] = 19;
                }

            }
        }
        
        return result;
    }
    
    private boolean inBounds(int x, int y, int width, int height) {
        return !(x < 0 || x >= width || y < 0 || y >= height);
    }
}