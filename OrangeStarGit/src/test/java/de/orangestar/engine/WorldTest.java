package de.orangestar.engine;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class WorldTest {
        
    @Before
    public void setup() {
        world      = new World();

        gameobject0 = new GameObject();
        gameobject1 = new GameObject();
        gameobject2 = new GameObject();
    }
    
    @Test
    public void addGameObject() {
        assertTrue(world.getGameObjects().isEmpty());
        
        world.addGameObject(gameobject0);
        assertEquals( world.getGameObjects().size(), 1);
        assertTrue( world.contains( gameobject0 ));
        
        world.addGameObject(gameobject1);
        assertEquals( world.getGameObjects().size(), 2);
        assertTrue( world.contains( gameobject1 ));
        
        world.addGameObject(gameobject2);
        assertEquals( world.getGameObjects().size(), 3);
        assertTrue( world.contains( gameobject2 ));
    }
    
    @Test
    public void removeGameObject() {
        assertTrue(world.getGameObjects().isEmpty());
        world.addGameObject(gameobject0);
        world.addGameObject(gameobject1);
        world.addGameObject(gameobject2);
        
        world.removeGameObject(gameobject0);
        assertEquals( world.getGameObjects().size(), 2);
        assertFalse( world.contains( gameobject0 ));
        
        world.removeGameObject(gameobject1);
        assertEquals( world.getGameObjects().size(), 1);
        assertFalse( world.contains( gameobject1 ));
        
        world.removeGameObject(gameobject2);
        assertEquals( world.getGameObjects().size(), 0);
        assertFalse( world.contains( gameobject2 ));
        
        assertTrue(world.getGameObjects().isEmpty());
    }
    
    @Test
    public void clear() {
        assertTrue(world.getGameObjects().isEmpty());
        world.addGameObject(gameobject0);
        world.addGameObject(gameobject1);
        world.addGameObject(gameobject2);
        
        assertEquals( world.getGameObjects().size(), 3);
        
        world.clear();

        assertTrue(world.getGameObjects().isEmpty());
    }
    
    @Test
    public void getGameObjects() {
        assertTrue( world.getGameObjects().isEmpty() );
        world.addGameObject( gameobject0 );
        world.addGameObject( gameobject1 );
        world.addGameObject( gameobject2 );
        
        List<GameObject> list = world.getGameObjects();
        
        assertNotNull( list );
        assertEquals( list.size(), 3 );
        assertTrue( list.contains(gameobject0) );
        assertTrue( list.contains(gameobject1) );
        assertTrue( list.contains(gameobject2) );
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void getGameObjects2() {
        world.getGameObjects().add( gameobject0 );
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void getGameObjects3() {
        world.getGameObjects().remove( gameobject0 );
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void getGameObjects4() {
        world.getGameObjects().clear();
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void getGameObjects5() {
        world.getGameObjects().addAll( Arrays.asList( gameobject0, gameobject1, gameobject2 ));
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void getGameObjects6() {
        world.getGameObjects().removeAll( Arrays.asList( gameobject0, gameobject1, gameobject2 ));
    }
    
 // TODO: Move this test to GameObject
//    @Test
//    public void getFirstGameObjectByTagsAny() {    
//        GameObject result;
//        
//        // 0# None gameobjects exists with given tag
//        result = world.getFirstByTagsAny("tag#0");
//        assertNull( result );
//        
//        // 0# Gameobjects exists with given tag
//        gameobject0.addTags("tag#0", "tag#1", "tag#2");
//        world.addGameObject(gameobject0);
//        
//        gameobject1.addTags("tag#0", "tag#1", "tag#2");
//        world.addGameObject(gameobject1);
//
//        gameobject2.addTags("tag#0", "tag#1", "tag#2");
//        world.addGameObject(gameobject2);
//        
//        result = world.getFirstByTagsAny("tag#0");
//        assertNotNull( result );
//    }
    
// TODO: Move this test to GameObject
//    @Test
//    public void getGameObjectsByTagsAny() {        
//        // Setup test
//        gameobject0.addTags("tag#0", "tag#1", "tag#2");
//        world.addGameObject(gameobject0);
//        
//        gameobject1.addTags("tag#2", "tag#3", "tag#4");
//        world.addGameObject(gameobject1);
//        
//        gameobject2.addTags("tag#4");
//        
//        
//        // #0 Get single gameobject by one matching tag
//        List<GameObject> result0 = world.getByTagsAny("tag#0");
//        assertTrue(result0.contains(gameobject0));
//        assertEquals(1, result0.size());
//        
//        // #1 Get empty result by no matching tag
//        List<GameObject> result1 = world.getByTagsAny("none");
//        assertTrue(result1.isEmpty());
//        
//        // #2 Get single gameobject by multiple matching tags
//        List<GameObject> result2 = world.getGameObjectsByTagsAny("tag#0", "tag#1");
//        assertEquals(result2.get(0), gameobject0);
//        assertEquals(1, result2.size());
//        
//        // #3 Get multiple gameobjects by single matching tag
//        List<GameObject> result3 = world.getByTagsAny("tag#2");
//        assertTrue(result3.contains(gameobject0));
//        assertTrue(result3.contains(gameobject1));
//        assertEquals(2, result3.size());
//        
//        // #4 Don't return gameobjects that aren't in the world
//        List<GameObject> result4 = world.getByTagsAny("tag#4");
//        assertTrue(result4.contains(gameobject1));
//        assertTrue(!result4.contains(gameobject2));
//        assertEquals(1, result4.size());
//    }
    
// TODO: Move this test to GameObject
//    @Test
//    public void getGameObjectByUID() {        
//        // Setup test
//        world.addGameObject(gameobject0);
//        world.addGameObject(gameobject1);
//        world.addGameObject(gameobject2);
//        
//        assertSame(gameobject0, world.getByUID( gameobject0.getUID() ));
//        assertSame(gameobject1, world.getByUID( gameobject1.getUID() ));
//        assertSame(gameobject2, world.getByUID( gameobject2.getUID() ));
//    }
        
    private GameObject gameobject0;
    private GameObject gameobject1;
    private GameObject gameobject2;
    private World      world;
    
}
