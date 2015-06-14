package de.orangestar.game.gameobjects.map;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.physics.component.UnitPhysicsComponent;
import de.orangestar.engine.tools.random.NoiseGenerator;
import de.orangestar.engine.tools.random.PerlinNoiseGenerator;

public class Map extends GameObject {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public Map() {  
        // Initialize Test Data
        MapChunk chunk_0_0 = new MapChunk();
        chunk_0_0.setData( generateTestData() );

        // Setup and link components
        MapLogicComponent   logic   = new MapLogicComponent(this);
        logic.addChunk(0, 0, chunk_0_0);
        
        UnitPhysicsComponent     physics = new UnitPhysicsComponent(this);
        MapInputComponent   input   = new MapInputComponent(this, physics);
        MapRenderComponent  render  = new MapRenderComponent(this, logic);

        // Set the components
        setLogicComponent(logic);
        setPhysicComponent(physics);
        setInputComponent(input);
        setRenderComponent(render);
        

    }
    
    private static MapSurface[][] generateTestData() {

        double[][] randomNoise = NoiseGenerator.translate(new PerlinNoiseGenerator().generate2DMap(MapChunk.CHUNK_SIZE, MapChunk.CHUNK_SIZE), -1d, 1d, 0d, 3d);

        MapSurface[][] data = new MapSurface[MapChunk.CHUNK_SIZE][MapChunk.CHUNK_SIZE];
        for(int x = 0; x < data.length; x++) {
            for(int y = 0; y < data[0].length; y++) {
                if (randomNoise[x][y] < 1d) {
                    data[x][y] = MapSurface.WATER;
                } else if(randomNoise[x][y] < 1.7d) {
                    data[x][y] = MapSurface.DIRT;
                } else {
                    data[x][y] = MapSurface.GRASS;
                }
            }
        }

        return data;
    }
}
