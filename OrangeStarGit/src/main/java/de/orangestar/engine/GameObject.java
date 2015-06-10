package de.orangestar.engine;

import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.logic.component.LogicComponent;
import de.orangestar.engine.physic.component.PhysicComponent;
import de.orangestar.engine.render.component.RenderComponent;

/**
 * Represents an object in the game world. This can be a player, an enemy, 
 * a tree, the worldmap or even the complete UI.
 * 
 * @author Basti
 */
public class GameObject {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public LogicComponent  getLogicModule() {
        return _moduleLogic;
    }

    public void 	       setLogicComponent(LogicComponent moduleLogic) {
        if (_moduleLogic != null) {
            _moduleLogic.setGameObject(null);
        }
        
        _moduleLogic = moduleLogic;
        
        if (_moduleLogic != null) {
            _moduleLogic.setGameObject(this);
        }
    }
    
    public RenderComponent getRenderModule() {
        return _moduleRender;
    }

    public void 	       setRenderComponent(RenderComponent moduleRender) {
        if (_moduleRender != null) {
            _moduleRender.setGameObject(null);
        }
        
        _moduleRender = moduleRender;
        
        if (_moduleRender != null) {
            _moduleRender.setGameObject(this);
        }
    }

    public InputComponent  getInputModule() {
        return _moduleInput;
    }

    public void 	       setInputComponent(InputComponent moduleInput) {
        _moduleInput = moduleInput;
    }
    
    public PhysicComponent getPhysicModule() {
        return _modulePhysic;
    }

    public void            setPhysicComponent(PhysicComponent modulePhysic) {
        _modulePhysic = modulePhysic;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private InputComponent     _moduleInput;
    private LogicComponent     _moduleLogic;
    private RenderComponent    _moduleRender;
    private PhysicComponent    _modulePhysic;

}
