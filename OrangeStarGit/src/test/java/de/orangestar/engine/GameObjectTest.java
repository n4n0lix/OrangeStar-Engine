package de.orangestar.engine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.orangestar.engine.input.InputComponent;
import de.orangestar.engine.logic.LogicComponent;
import de.orangestar.engine.physics.PhysicsComponent;
import de.orangestar.engine.render.Camera;
import de.orangestar.engine.render.IRenderEngine;
import de.orangestar.engine.render.RenderComponent;

public class GameObjectTest {

    @Before
    public void setup() {
        fakeLogicComponent   = new FakeLogicComponent();
        fakeRenderComponent  = new FakeRenderComponent();
        fakeInputComponent   = new FakeInputComponent();
        
        gameobject = new GameObject();
        gameobject.setLogicComponent(fakeLogicComponent);
        gameobject.setRenderComponent(fakeRenderComponent);
        gameobject.setInputComponent(fakeInputComponent);
    }
    
    @Test
    public void addTags() {        
        // Initially empty
        assertTrue(gameobject.getTags().isEmpty());
        
        // Add tags
        gameobject.addTags("tag#0", "tag#1", "tag#2");
        assertEquals(gameobject.getTags().size(), 3);
        assertTrue(gameobject.getTags().contains("tag#0"));
        assertTrue(gameobject.getTags().contains("tag#1"));
        assertTrue(gameobject.getTags().contains("tag#2"));
        
        // Add duplicate tag
        gameobject.addTags("tag#0");
        assertEquals(gameobject.getTags().size(), 3);
        assertTrue(gameobject.getTags().contains("tag#0"));
        assertTrue(gameobject.getTags().contains("tag#1"));
        assertTrue(gameobject.getTags().contains("tag#2"));
    }
    
    @Test
    public void removeTags() {        
        // Initially empty
        assertTrue(gameobject.getTags().isEmpty());
        
        // Add tags
        gameobject.addTags("tag#0", "tag#1", "tag#2");
        assertEquals(gameobject.getTags().size(), 3);
        assertTrue(gameobject.getTags().contains("tag#0"));
        assertTrue(gameobject.getTags().contains("tag#1"));
        assertTrue(gameobject.getTags().contains("tag#2"));
        
        // Add duplicate tag
        gameobject.removeTags("tag#0", "tag#1");
        assertEquals(gameobject.getTags().size(), 1);
        assertFalse(gameobject.getTags().contains("tag#0"));
        assertFalse(gameobject.getTags().contains("tag#1"));
        assertTrue(gameobject.getTags().contains("tag#2"));
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void tags1() {        
        gameobject.getTags().add("#tag0");
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void tags2() {        
        gameobject.getTags().remove("#tag0");
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void tags3() {        
        gameobject.getTags().addAll( Arrays.asList( "#tag0", "#tag1" ));
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void tags4() {        
        gameobject.getTags().removeAll( Arrays.asList( "#tag0", "#tag1" ));
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void tags5() {        
        gameobject.getTags().clear();
    }
    
    @Test
    public void hasComponent() {
        assertTrue( gameobject.hasComponent( LogicComponent.class ));
        assertTrue( gameobject.hasComponent( RenderComponent.class ));
        assertTrue( gameobject.hasComponent( InputComponent.class ));
        
        assertFalse( gameobject.hasComponent( PhysicsComponent.class ));
    }
    
    @Test
    public void getComponent() {
        assertSame( fakeLogicComponent,  gameobject.getComponent( LogicComponent.class ));
        assertSame( fakeRenderComponent, gameobject.getComponent( RenderComponent.class ));
        assertSame( fakeInputComponent,  gameobject.getComponent( InputComponent.class ));
        
        assertNull( gameobject.getComponent( PhysicsComponent.class ) );
    }
        
    private GameObject gameobject;
    
    private LogicComponent fakeLogicComponent;
    private RenderComponent fakeRenderComponent;
    private InputComponent fakeInputComponent;
    
    private class FakeLogicComponent extends LogicComponent {

        @Override
        protected void onInitialize() { }
        @Override
        protected void onDeinitialize() { }
        
    }
    
    private class FakeInputComponent extends InputComponent {

        @Override
        public void onUpdate() { }
        @Override
        protected void onInitialize() { }
        @Override
        protected void onDeinitialize() { }

    }
    
    private class FakeRenderComponent extends RenderComponent {

        @Override
        public void onRender(IRenderEngine engine, Camera camera) { }
        @Override
        protected void onInitialize() { }
        @Override
        protected void onDeinitialize() { }
        
    }
}
