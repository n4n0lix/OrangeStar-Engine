package de.orangestar.game.gameobjects.props;

import java.util.HashMap;
import java.util.Map;

import de.orangestar.engine.World;
import de.orangestar.engine.logic.LogicComponent;
import de.orangestar.engine.render.RenderComponent;
import de.orangestar.engine.values.Transform;
import de.orangestar.engine.values.Vector3f;

/**
 * Logic component of the PropsModel.
 * 
 * @author Oliver &amp; Basti
 */
public class PropsModelLogicComponent extends LogicComponent {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /**
     * Spawner class for the props.
     * 
     * @author Oliver &amp; Basti
     */
    public class PropsSpawner {
        
        public PropsSpawner(PropsType type) {
            _propsType = type;
        }
        
        /**
         * Spawns a prop with a vector3f coordinate.
         * 
         * @param position where the Props should be spawned
         */
        public void spawnProps(Vector3f position) {
            spawnProps(new Transform(position));
        }
        
        /**
         * Spawns a prop with a transform coordinate.
         * 
         * @param transform where the Props should be spawned
         */
        public void spawnProps(Transform transform) {
            // 1# Create Props Flyweight
            PropsFlyweight flyweight = new PropsFlyweight(_propsType);
            Transform.set( flyweight.getLocalTransform(), transform);
            getGameObject().getWorld().addGameObject(flyweight);
            
            // 3# Add ItemModel rendering instance
            RenderComponent render = getGameObject().getRenderComponent();
            
            if (render != null && render instanceof PropsModelRenderComponent) {
                PropsModelRenderComponent itemModelRender = (PropsModelRenderComponent) render;
                
                itemModelRender.addPropsInstance(_propsType, transform);
            }
            
        }
        
        /**
         * 
         * @return the Type of the Prop
         */
        public PropsType getPrototype() {
            return _propsType;
        }
        
        private PropsType _propsType;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

	/**
	 * Returns a props spawner that is capable of spawning the given props type.
	 * @param propsType The props type
	 * @return A props spawner
	 */
	public PropsSpawner getPropsSpawner(PropsType propsType) {
		PropsSpawner spawner;
		
    	if(!_spawnerMap.containsKey(propsType)) {
    		spawner = new PropsSpawner(propsType);
    		_spawnerMap.put(propsType, spawner);    		
    	} else {
    		spawner = _spawnerMap.get(propsType);
    	}
    	
    	return spawner;
	}
	
	@Override
    protected void onInitialize() { }
	
	@Override
    protected void onDeinitialize() { }

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private Map<PropsType, PropsSpawner> _spawnerMap = new HashMap<>();

}
