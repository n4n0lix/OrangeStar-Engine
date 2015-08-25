package de.orangestar.game.gameobjects.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import de.orangestar.engine.GameObject;

public class TreeTest {

    @Before
    public void before() {   
        fakeGameObject = new GameObject();
        modelLogic     = new TreeModelLogicComponent();
        
        fakeGameObject.setLogicComponent(modelLogic);
    }
    
    @Test
    public void addFlyweight() {
        // 1# Flyweight
        TreeFlyweight flyweight1 = new TreeFlyweight();
        
        modelLogic.addFlyweight(flyweight1);
        assertEquals(1, modelLogic.getTrees().size());
        assertTrue(modelLogic.getTrees().contains(flyweight1));
        
        // 2# Flyweight
        TreeFlyweight flyweight2 = new TreeFlyweight();
        
        modelLogic.addFlyweight(flyweight2);
        assertEquals(2, modelLogic.getTrees().size());
        assertTrue(modelLogic.getTrees().contains(flyweight2));
        
        // 3# Flyweight
        TreeFlyweight flyweight3 = new TreeFlyweight();
        
        modelLogic.addFlyweight(flyweight3);
        assertEquals(3, modelLogic.getTrees().size());
        assertTrue(modelLogic.getTrees().contains(flyweight3));
    }
    
    @Test
    public void removeFlyweight() {
        TreeFlyweight flyweight1 = new TreeFlyweight();
        TreeFlyweight flyweight2 = new TreeFlyweight();
        TreeFlyweight flyweight3 = new TreeFlyweight();
        
        modelLogic.addFlyweight(flyweight1);
        modelLogic.addFlyweight(flyweight2);
        modelLogic.addFlyweight(flyweight3);
        
        assertEquals(modelLogic.getTrees().size(), 3);

        // 1# Flyweight remove
        modelLogic.removeFlyweightByUID(flyweight1.getUID());
        assertFalse(modelLogic.getTrees().contains(flyweight1));
        assertEquals(2, modelLogic.getTrees().size());
        
        // 2# Flyweight remove
        modelLogic.removeFlyweightByUID(flyweight2.getUID());
        assertFalse(modelLogic.getTrees().contains(flyweight2));
        assertEquals(1, modelLogic.getTrees().size());
        
        // 3# Flyweight remove
        modelLogic.removeFlyweightByUID(flyweight3.getUID());
        assertFalse(modelLogic.getTrees().contains(flyweight3));
        assertEquals(0, modelLogic.getTrees().size());
    }

    private GameObject              fakeGameObject;
    private TreeModelLogicComponent modelLogic;
    
}
