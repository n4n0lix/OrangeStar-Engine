package de.orangestar.engine;

import static org.junit.Assert.assertTrue;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.junit.Before;
import org.junit.Test;

import de.orangestar.engine.logic.LogicComponent;

public class ComponentTest {
        
    @Before
    public void setup() {
        gameobject = new GameObject();
    }
    
    @Test
    public void lifecycleComponent() {
        // #1 Setup test
        final MutableBoolean initialized = new MutableBoolean( false );
        final MutableBoolean updated     = new MutableBoolean( false );
        final MutableBoolean cleanUp     = new MutableBoolean( false );
        
        LogicComponent fakeComponent = new LogicComponent() {
            @Override
            public void onInitialize() { initialized.setValue(true); }
            
            @Override
            public void onUpdate() { updated.setValue(true); }
            
            @Override
            public void onDeinitialize() { cleanUp.setValue(true); }
        };
        
        // 2# Execute a component lifecycle
        gameobject.setLogicComponent( fakeComponent );
        gameobject.getLogicComponent().onUpdate();
        gameobject.setLogicComponent( null );
        
        // 3# Check results
        assertTrue( initialized.booleanValue() );
        assertTrue( updated.booleanValue() );
        assertTrue( cleanUp.booleanValue() );
    }

    private GameObject gameobject;

}
