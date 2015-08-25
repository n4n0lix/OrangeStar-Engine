package de.orangestar.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.orangestar.engine.input.InputComponent;
import de.orangestar.engine.logic.LogicComponent;
import de.orangestar.engine.physics.PhysicsComponent;
import de.orangestar.engine.render.RenderComponent;
import de.orangestar.engine.values.Transform;

/**
 * Represents an object in the game world. This can be a player, an enemy, 
 * a tree, the worldmap or even the complete UI.
 * 
 * @author Oliver &amp; Basti
 */
public class GameObject {
    
    /**
     * Returns a gameobject by its uid.
     * @param uid A gameobject uid
     * @return A gameobject or null if none was found
     */
    public static GameObject getByUID(long uid) {
        return MAP_BY_UIDS.get(uid);
    }
    
    /**
     * Returns the first gameobject found that matches any of the given tags.
     * @param tags Tags
     * @return A gameobject or null if none was found
     */
    public static GameObject getFirstByTagsAny(String... tags) {
        List<GameObject> objs = getByTagsAny(tags);
        
        if (objs != null && !objs.isEmpty()) {
            return objs.get(0);
        }
        
        return null;
    }
    
    /**
     * Returns a list of all gameobjects that match atleast one of the given tags.
     * @param tags Strings that are representing tags
     * @return A list of gameobjects, no duplicates
     */
    public static List<GameObject> getByTagsAny(String... tags) {
        Set<GameObject> result = new HashSet<>();
        
        for(String hashtag : tags) {
            Set<GameObject> objects = MAP_BY_TAGS.get(hashtag);
            
            if (objects != null && !objects.isEmpty()) {
                result.addAll( objects );
            }
        }
        
        return new ArrayList<>(result);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor.
     */
    public GameObject() {
    	_uID = generateUID();
    	MAP_BY_UIDS.put(_uID, this);
    	
        _name = "GameObject#" + _uID;
    	
        _children = new ArrayList<>();
        
        _tags 			= new HashSet<>();
        _readOnlyTags	= Collections.unmodifiableSet(_tags);

    	_localTransform      = new Transform();
    	_lastGlobalTransform = new Transform();
    }

    /**
     * Returns the unique id of this gameobject.
     * @return The unique id 
     */
    public long getUID() {
    	return _uID;
    }
    
    /**
     * Returns the name.
     * @return The name
     */
    public String getName() {
        return _name;
    }

    /**
     * Sets the name.
     * @param name The name
     */
    public void setName(String name) {
        _name = name;
    }

    /* Tags */
    /**
     * Returns all tags that this gameobject has.
     * @return A list of tags
     */
    public final Set<String> getTags() {
        return _readOnlyTags;
    }
    
    /**
     * Adds the given tags to this gameobject.
     * @param tags Tags
     */
    public final void addTags(String... tags) {
    	for(String tag : tags) {    	    
    	    addGameObjectTag(tag, this);
    	}
    }
    
    /**
     * Removes the given tags from this gameobject.
     * @param tags Tags
     */
    public final void removeTags(String... tags) {
        for(String tag : tags) {            
            removeGameObjectTag(tag, this);
        }
    }
    
    /**
     * Checks if this gameobject has a component of the given class.
     * @param clazz The component subclass
     * @return If this gameobject has a component of the given class
     */
    public final boolean hasComponent(Class<? extends Component> clazz) {
        
        // LogicComponent
        if (_componentLogic != null && clazz.isInstance(_componentLogic)) {
            return true;
        }
        
        // RenderComponent
        if (_componentRender != null && clazz.isInstance(_componentRender)) {
            return true;
        }
        
        // PhysicsComponent
        if (_componentPhysics != null && clazz.isInstance(_componentPhysics)) {
            return true;
        }
        
        // InputComponent
        if (_componentInput != null && clazz.isInstance(_componentInput)) {
            return true;
        }
        
        return false;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> clazz) {
        
        // LogicComponent
        if (_componentLogic != null && clazz.isInstance(_componentLogic)) {
            return (T) _componentLogic;
        }
        
        // RenderComponent
        if (_componentRender != null && clazz.isInstance(_componentRender)) {
            return (T) _componentRender;
        }
        
        // PhysicsComponent
        if (_componentPhysics != null && clazz.isInstance(_componentPhysics)) {
            return (T) _componentPhysics;
        }
        
        // InputComponent
        if (_componentInput != null && clazz.isInstance(_componentInput)) {
            return (T) _componentInput;
        }
        
        return null;
    }
    
    /* Components */
    /**
     * Returns the logic component.
     * @return A logic component or null
     */
    public LogicComponent  getLogicComponent() {
        return _componentLogic;
    }
    
    /**
     * Returns the render component.
     * @return The render component or null
     */
    public RenderComponent getRenderComponent() {
        return _componentRender;
    }

    /**
     * Returns the input component
     * @return The input component or null
     */
    public InputComponent  getInputComponent() {
        return _componentInput;
    }

    /**
     * Returns the physics component
     * @return The physics component or null
     */
    public PhysicsComponent getPhysicsComponent() {
        return _componentPhysics;
    }


    /**
     * Returns the local transform (the offset/rotation in local game object space).
     * @return The local Transform.
     */
    public Transform getLocalTransform() {
		return _localTransform;
	}
    
    /**
     * Sets the local transform (the offset/rotation in local game object space).
     * @param t the local transform
     */
	public void setLocalTransform(Transform t) {
	    Transform.set(_localTransform, t);
	}
	
	/**
	 * Returns the global transformation (the offset/rotation in world space)
	 * @return The global transformation
	 */
	public Transform getGlobalTransform() {
		if (_parent != null) {
		    Transform.set(_combinedTransform, _parent._localTransform);
		    Transform.combine(_combinedTransform, _localTransform);
		}
		
		return _localTransform;
	}
	
	/**
	 * Returns the global transformation in the last tick.
	 * @return The global transformation in the last tick
	 */
	public Transform getGlobalLastTransform() {
		return _lastGlobalTransform;
	}
	
	/**
	 * Sets the global transformation in the last tick.
	 * @param t The global transformation in the last tick
	 */
	public void setGlobalLastTransform(Transform transform) {
        _lastGlobalTransform.position.x = transform.position.x;
        _lastGlobalTransform.position.y = transform.position.y;
        _lastGlobalTransform.position.z = transform.position.z;
        
        _lastGlobalTransform.scale.x = transform.scale.x;
        _lastGlobalTransform.scale.y = transform.scale.y;
        _lastGlobalTransform.scale.z = transform.scale.z;

        _lastGlobalTransform.rotation.x = transform.rotation.x;
        _lastGlobalTransform.rotation.y = transform.rotation.y;
        _lastGlobalTransform.rotation.w = transform.rotation.w;
        _lastGlobalTransform.rotation.z = transform.rotation.z;
	}

	/**
	 * Adds a child.
	 * @param gameObject A child gameobject
	 */
    public void addChild(GameObject gameObject) {
        _children.add(gameObject);
        gameObject._parent = this;
    }

    /**
     * Removes a child.
     * @param gameObject A child gameobject
     */
    public void removeChild(GameObject gameObject) {
        _children.remove(gameObject);
        gameObject._parent = null;
    }
    
    /**
     * Returns a read-only list of all children.
     * @return A read-only list of all children
     */
    public List<GameObject> getChildren () {
        return Collections.unmodifiableList(_children);
    }
    
    public World getWorld() {
        return _world;
    }
    
    /**
     * Destroys this game object, all it's components and all it's children.
     */
    public void destroy() {
        if (_componentInput != null) {
            _componentInput.onDeinitialize();
        }
        
        if (_componentLogic != null) {
            _componentLogic.onDeinitialize();
        }
        
        if (_componentPhysics != null) {
            _componentPhysics.onDeinitialize();
        }
        
        if (_componentRender != null) {
            _componentRender.onDeinitialize();
        }
    	
    	_parent.removeChild(this);
    	for(GameObject child: _children) {
    		child.destroy();
    	}
    }

    /**
     * Sets the logic component.
     * @param moduleLogic The logic component
     */
    public void setLogicComponent(LogicComponent moduleLogic) {
        if (_componentLogic != null) {
            _componentLogic.onDeinitialize();
            ((Component) _componentLogic).setGameObject(null);
        }
        
        _componentLogic = moduleLogic;
        
        if (_componentLogic != null) {
            ((Component) _componentLogic).setGameObject(this);
            _componentLogic.onInitialize();
        }
    }
    
    /**
     * Sets the render component
     * @param moduleRender The render component
     */
    public void setRenderComponent(RenderComponent moduleRender) {
        if (_componentRender != null) {
            _componentRender.onDeinitialize();
            ((Component) _componentRender).setGameObject(null);
        }
        
        _componentRender = moduleRender;
        
        if (_componentRender != null) {
            ((Component) _componentRender).setGameObject(this);
            _componentRender.onInitialize();
        }
    }
    
    /**
     * Sets the physics component
     * @param compPhysics The physics component
     */
    public void setPhysicsComponent(PhysicsComponent compPhysics) {
        if (_componentPhysics != null) {
            _componentPhysics.onDeinitialize();
            ((Component) _componentPhysics).setGameObject(null);
        }
        
        _componentPhysics = compPhysics;
        
        if (_componentPhysics != null) {
            ((Component) _componentPhysics).setGameObject(this);
            _componentPhysics.onInitialize();
        }
    }
    
    /**
     * Sets the input component
     * @param compInput The input component
     */
    public void setInputComponent(InputComponent compInput) {
        if (_componentInput != null) {
            _componentInput.onDeinitialize();
            ((Component) _componentInput).setGameObject(null);
        }
        
        _componentInput = compInput;
        
        if (_componentInput != null) {
            ((Component) _componentInput).setGameObject(this);
            _componentInput.onInitialize();
        }
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Package                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    void setWorld(World world) {
        _world = world;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private long				_uID;
    private String              _name;
    private Set<String>         _tags;
    private Set<String>		    _readOnlyTags;
    
    private World               _world;
    private GameObject          _parent;
    private List<GameObject>    _children;

    private Transform           _localTransform      = new Transform();
	private Transform 			_lastGlobalTransform = new Transform(); 

    private InputComponent     	_componentInput;
    private LogicComponent     	_componentLogic;
    private RenderComponent    	_componentRender;
    private PhysicsComponent    _componentPhysics;
    
    // Instance reusing
    private Transform _combinedTransform = new Transform();

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Generates a unique id.
     * @return A unique id
     */
    private static long generateUID() {
        return UID_COUNTER++;
    }
    
    private static void addGameObjectTag(String tag, GameObject obj) {
        Set<GameObject> objects = MAP_BY_TAGS.get(tag);
        
        if (objects == null) {
            objects = new HashSet<>();
            MAP_BY_TAGS.put(tag, objects);
        }
        
        obj._tags.add(tag);
        objects.add(obj);
    }
    
    private static void removeGameObjectTag(String tag, GameObject obj) {
        Set<GameObject> objects = MAP_BY_TAGS.get(tag);
        
        if (objects == null) {
            return;
        }
        
        obj._tags.remove(tag);
        objects.remove(obj);
    }

    private static long UID_COUNTER;
    private static HashMap<Long, GameObject>         MAP_BY_UIDS = new HashMap<>();
    private static HashMap<String, Set<GameObject>>  MAP_BY_TAGS = new HashMap<>();
}
