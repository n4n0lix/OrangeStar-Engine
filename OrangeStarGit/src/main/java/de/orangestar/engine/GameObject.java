package de.orangestar.engine;

import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.logic.component.LogicComponent;
import de.orangestar.engine.render.component.RenderComponent;
import de.orangestar.engine.values.Transform;

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

    public void 	       setLogicModule(LogicComponent moduleLogic) {
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

    public void 	       setRenderModule(RenderComponent moduleRender) {
        if (_moduleRender != null) {
            _moduleRender.setGameObject(null);
        }
        
        _moduleRender = moduleRender;
        
        if (_moduleRender != null) {
            _moduleRender.setGameObject(this);
        }
    }

    public InputComponent getInputModule() {
        return _moduleInput;
    }

    public void setInputModule(InputComponent moduleInput) {
        _moduleInput = moduleInput;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private InputComponent     _moduleInput;
    private LogicComponent     _moduleLogic;
    private RenderComponent    _moduleRender;


}
