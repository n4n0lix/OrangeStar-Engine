package de.orangestar.game.gameobjects.props;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.actor.InstancedImage;
import de.orangestar.engine.render.component.UnitRenderComponent;
import de.orangestar.engine.values.Anchor;
import de.orangestar.engine.values.Transform;
import de.orangestar.game.MainGameState;

/**
 * Render component of the PropsModel.
 * 
 * @author Oliver &amp; Basti
 */
public class PropsModelRenderComponent extends UnitRenderComponent {

	@Override
	public void onInitialize() {
	    super.onInitialize();
	    
	    setLayer(MainGameState.LAYER_PROPS);
	}

	/**
	 * Adds and renders another instance of the prop.
	 * 
	 * @param propsType the instance to render
	 * @param position Where to render the prop
	 */
	public void addPropsInstance(PropsType propsType, Transform position) {
		InstancedImage actor;
		
		if(!_renderMap.containsKey(propsType)) {
		    Texture propsTexture = new Texture( propsType.getTexturePath(), true );
		    float   propsWidth   = propsType.getWidth();
		    float   propsHeight  = propsType.getHeight();
		    Anchor  propsAnchor  = propsType.getAnchor();
		    
			actor = new InstancedImage.Builder()
											.texture( propsTexture )
											.size( propsWidth, propsHeight )
											.anchor( propsAnchor )
											.sorted(Depth2DComparator)
											.build();
			addActor(actor);
			_renderMap.put(propsType, actor);
		} else {
			actor = _renderMap.get(propsType);
		}
		
		actor.addInstance(position);
	}

	private Map<PropsType, InstancedImage> _renderMap = new HashMap<>();
	
    private Comparator<Transform> Depth2DComparator = new Comparator<Transform>() {
        @Override
        public int compare(Transform o1, Transform o2) {
            return Float.compare(o1.position.y, o2.position.y);
        }
    };
}
