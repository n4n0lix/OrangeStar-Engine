package de.orangestar.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.logic.component.LogicComponent;
import de.orangestar.engine.physics.component.PhysicsComponent;
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
    
    public GameObject() {
    	_transform =  new Transform();
    	_children = new ArrayList<>();
    	_lastGlobalTransform = new Transform();
    }
	
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
    
    public PhysicsComponent getPhysicsModule() {
        return _modulePhysic;
    }

    public void            setPhysicComponent(PhysicsComponent modulePhysic) {
        _modulePhysic = modulePhysic;
    }
    
    public Transform getLocalTransform() {
		return _transform;
	}
    
	public void setLocalTransform(Transform t) {
		_transform = t;
	}
	
	public void addChild(GameObject gameObject) {
		_children.add(gameObject);
		gameObject._parent = this;
	}
	
	public void removeChild(GameObject gameObject) {
		_children.remove(gameObject);
		gameObject._parent = null;
	}
	
	public List<GameObject> getChildren () {
		return Collections.unmodifiableList(_children);
	}
	
	public Transform getGlobalTransform() {
		if (_parent != null) {
			return _parent._transform.combine(_transform);
		}
		
		return _transform;
	}
	
	public Transform getGlobalLastTransform() {
		return _lastGlobalTransform;
	}
	
	public void setGlobalLastTransform(Transform t) {
		_lastGlobalTransform = t;
	}
	
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
	private Transform 			_lastGlobalTransform; 
	private GameObject			_parent;
    private List<GameObject>	_children;
	private Transform 			_transform;
    private InputComponent     	_moduleInput;
    private LogicComponent     	_moduleLogic;
    private RenderComponent    	_moduleRender;
    private PhysicsComponent    _modulePhysic;

}
