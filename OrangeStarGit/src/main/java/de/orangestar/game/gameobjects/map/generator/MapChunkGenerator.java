package de.orangestar.game.gameobjects.map.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;
import de.orangestar.engine.render.actor.tilemap.Tile;
import de.orangestar.engine.tools.noise.NoiseMapGen;
import de.orangestar.engine.tools.noise.PerlinNoiseMapGen;
import de.orangestar.engine.tools.random.RandomFunc;
import de.orangestar.engine.tools.random.SimplexRandomFunc;
import de.orangestar.engine.utils.ArrayUtils;
import de.orangestar.engine.values.Quaternion4f;
import de.orangestar.engine.values.Transform;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.game.MainGameState;
import de.orangestar.game.gameobjects.map.MapChunk;
import de.orangestar.game.gameobjects.map.surfaces.DeepWater;
import de.orangestar.game.gameobjects.map.surfaces.Grass;
import de.orangestar.game.gameobjects.map.surfaces.Sand;
import de.orangestar.game.gameobjects.map.surfaces.Water;
import de.orangestar.game.gameobjects.props.PropsModelLogicComponent;
import de.orangestar.game.gameobjects.props.PropsType;
import de.orangestar.game.gameobjects.props.PropsModelLogicComponent.PropsSpawner;
import de.orangestar.game.gameobjects.props.prototypes.Bush1;
import de.orangestar.game.gameobjects.props.prototypes.Bush2;
import de.orangestar.game.gameobjects.props.prototypes.Flowers1;
import de.orangestar.game.gameobjects.props.prototypes.Seastar1;
import de.orangestar.game.gameobjects.props.prototypes.SmallRock1;
import de.orangestar.game.gameobjects.tree.TreeFlyweight;
import de.orangestar.game.gameobjects.tree.TreeModel;

/**
 * This class generates the MapChunks for the Map.
 * 
 * @author Oliver &amp; Basti
 */
public class MapChunkGenerator {
    
    public final static float TREE_DENSITY    = 0.45f;
    
    public final static float DEEPWATER_LEVEL = 0.2f;  // 0.0  <-> 0.2
    public final static float WATER_LEVEL     = 0.3f;  // 0.2  <-> 0.3
    public final static float SAND_LEVEL      = 0.35f; // 0.3  <-> 0.35
    public final static float GRASS_LEVEL     = 1.0f;  // 0.35 <-> 1.0
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Creates a MapChunk with a pseudorandom seed.
     */
    public MapChunkGenerator() {
        this(System.currentTimeMillis());
    }
    
    /**
     * Creates a MapChunk with a given seed.
     * 
     * @param seed determines how what Tiles the Chunk contains, the same seed means the same Chunk
     */
    public MapChunkGenerator(long seed) {
        _seed = seed;
    }
    
    /**
     * Generates the Chunks and returns the Chunk.
     * 
     * @param x size on the x-Axis
     * @param y size on the y-Axis
     * @return the generated Chunk
     */
    public MapChunk generateMapChunk(int x, int y) {
        MapChunk chunk = new MapChunk(x,y);
        
        double[][] randomNoise = generateNoiseMap(chunk);
        
        generateTiles(chunk, randomNoise);
        generateProps(chunk, randomNoise);        
        generateTrees(chunk, randomNoise);

        return chunk;
    }
    
    /**
     * Sets the seed.
     * 
     * @param seed determines the composition of the Chunk
     */
    public void setSeed(long seed) {
        _seed = seed;
    }
    
    /**
     * Returns the seed.
     * 
     * @return the seed
     */
    public long getSeed() {
        return _seed;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private double[][] generateNoiseMap(MapChunk chunk) {
        double[][] randomNoise;
        
        double rndWidth  = MapChunk.CHUNK_SIZE * .05d;
        double rndHeight = MapChunk.CHUNK_SIZE * .05d;
        double rndX      = chunk.getX() * rndWidth;
        double rndY      = chunk.getY() * rndHeight;
        
        randomNoise = NOISEMAP_GEN.get(MapChunk.CHUNK_SIZE, MapChunk.CHUNK_SIZE, RANDOM_FUNC, rndX, rndY, rndWidth, rndHeight, _seed);
        ArrayUtils.translate( randomNoise, -1d, 1d, 0d, 1d);
        
        return randomNoise;
    }
    
    private void generateTiles(MapChunk chunk, double[][] randomNoise) {
        Tile[][] tileData = new Tile[MapChunk.CHUNK_SIZE][MapChunk.CHUNK_SIZE];
        
        for (int x = 0; x < tileData.length; x++) {
            for (int y = 0; y < tileData[0].length; y++) {
                if (randomNoise[x][y] < DEEPWATER_LEVEL) {
                    tileData[x][y] = MapChunk.generateRandomTile(DeepWater.Instance);
                }
                else if (randomNoise[x][y] < WATER_LEVEL) {
                    tileData[x][y] = MapChunk.generateRandomTile(Water.Instance);
                }
                else if (randomNoise[x][y] < SAND_LEVEL) {
                    tileData[x][y] = MapChunk.generateRandomTile(Sand.Instance);
                }
                else {
                    tileData[x][y] = MapChunk.generateRandomTile(Grass.Instance);
                }
            }
        }

        chunk.setTileData(tileData);
    }
    
    private void generateProps(MapChunk chunk, double[][] randomNoise) {
        // 1# Get a PropsModel instance
        GameObject obj = GameObject.getFirstByTagsAny("propsmodel");

        if (obj == null) {
            return;
        }
        
        // 2# Get its PropsModelLogicComponent
        PropsModelLogicComponent propsLogic = obj.getComponent(PropsModelLogicComponent.class);

        if (propsLogic == null) {
            return;
        }

        List<PropsSpawner> grassSpawners = new ArrayList<>();
        grassSpawners.add(propsLogic.getPropsSpawner(Flowers1.Instance));
        grassSpawners.add(propsLogic.getPropsSpawner(SmallRock1.Instance));
        grassSpawners.add(propsLogic.getPropsSpawner(Bush1.Instance));
        grassSpawners.add(propsLogic.getPropsSpawner(Bush2.Instance));
        
        List<PropsSpawner> sandSpawners = new ArrayList<>();
        sandSpawners.add(propsLogic.getPropsSpawner(Seastar1.Instance));
        
        // 3# Generate props
        for(int tileX = 0; tileX < randomNoise.length; tileX++) {
            for(int tileY = 0; tileY < randomNoise[tileX].length; tileY++) {
                double noiseValue = randomNoise[tileX][tileY];
                // Only spawn props on land
                if(noiseValue > WATER_LEVEL && noiseValue < SAND_LEVEL) {
                    for(PropsSpawner spawner : sandSpawners) {
                        PropsType prototype = spawner.getPrototype();
                        
                        if (RANDOM.nextFloat() < prototype.getGenerationProbability()) {
                            float rndScale = RANDOM.nextFloat() * (prototype.getMaxScale() - prototype.getMinScale()) + prototype.getMinScale();
                            Transform transform = new Transform();
                            
                            if (prototype.isRotatable()) {
                                Quaternion4f.rotationAxis(Vector3f.Z_AXIS, RANDOM.nextFloat() * 360f, transform.rotation);
                            }
                            
                            Vector3f.set(transform.scale, rndScale, rndScale, 1f);
                            Vector3f.set(transform.position, generatePropsPosition(chunk.getX(), chunk.getY(), tileX, tileY));
                            
                            spawner.spawnProps(transform);
                        }
                    }
                }
                else if(noiseValue > SAND_LEVEL && noiseValue < GRASS_LEVEL) {
                    for(PropsSpawner spawner : grassSpawners) {
                        PropsType prototype = spawner.getPrototype();
                        
                        if (RANDOM.nextFloat() < prototype.getGenerationProbability()) {
                            float rndScale = RANDOM.nextFloat() * (prototype.getMaxScale() - prototype.getMinScale()) + prototype.getMinScale();
                            Transform transform = new Transform();
                            
                            if (prototype.isRotatable()) {
                                Quaternion4f.rotationAxis(Vector3f.Z_AXIS, RANDOM.nextFloat() * 360f, transform.rotation);
                            }
                            
                            Vector3f.set(transform.scale, rndScale, rndScale, 1f);
                            Vector3f.set(transform.position, generatePropsPosition(chunk.getX(), chunk.getY(), tileX, tileY));
                            
                            spawner.spawnProps(transform);
                        }
                    }
                }
            }
        }

    }

    private void generateTrees(MapChunk chunk, double[][] randomNoise) {
        List<TreeFlyweight> trees = new ArrayList<>();
        
        for(int i = 0; i < randomNoise.length; i++) {
            for(int j = 0; j < randomNoise[i].length; j++) {

                double  noiseVal = randomNoise[i][j];
                boolean isGrass  = noiseVal > SAND_LEVEL;
                boolean rndCheckPassed = (RANDOM.nextFloat() *noiseVal ) > 1-TREE_DENSITY;
                
                if(isGrass && rndCheckPassed) {
                    TreeFlyweight tree = new TreeFlyweight();

                    Vector3f position = generatePropsPosition(chunk.getX(), chunk.getY(), i, j);
                    tree.setLocalTransform(new Transform(position, Vector3f.one(), Quaternion4f.identity()));
                    
                    MainGameState.GAME_WORLD.addGameObject(tree);
                    trees.add(tree);
                }
            }
        }
        
        // Add Trees to TreeModel
        List<GameObject> gameObjects = GameObject.getByTagsAny("treemodel");
        
        if (gameObjects != null && !gameObjects.isEmpty() && (gameObjects.get(0) instanceof TreeModel)) {
            TreeModel forest = (TreeModel) gameObjects.get(0);
            forest.getLogicComponent().addFlyweights(trees);
        }
        

    }
    
    private long _seed;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private static Vector3f generatePropsPosition(int chunkX, int chunkY, int tileX, int tileY) {
        float worldXOffset = chunkX * MapChunk.CHUNK_SIZE * MapChunk.TILE_SIZE;
        float worldYOffset = chunkY * MapChunk.CHUNK_SIZE * MapChunk.TILE_SIZE;
        
        float tileXOffset = tileX * MapChunk.TILE_SIZE;
        float tileYOffset = tileY * MapChunk.TILE_SIZE;

        float rndXOffset   = 4 + RANDOM.nextInt(((int) MapChunk.TILE_SIZE) - 8);
        float rndYOffset   = 4 + RANDOM.nextInt(((int) MapChunk.TILE_SIZE) - 8);
        
        Vector3f position = new Vector3f(worldXOffset + tileXOffset + rndXOffset, worldYOffset + tileYOffset + rndYOffset, 0.0f);
        return position;
    }
    
    private static Random       RANDOM          = new Random();
    private static RandomFunc   RANDOM_FUNC     = new SimplexRandomFunc();
    private static NoiseMapGen  NOISEMAP_GEN    = new PerlinNoiseMapGen();
    
}
