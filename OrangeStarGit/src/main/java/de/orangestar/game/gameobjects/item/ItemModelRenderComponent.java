package de.orangestar.game.gameobjects.item;

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
 * RenderComponent for the ItemModel.
 * 
 * @author Oliver &amp; Basti
 */
public class ItemModelRenderComponent extends UnitRenderComponent {

	@Override
    protected void onInitialize() {
	    super.onInitialize();
	    
	    setLayer(MainGameState.LAYER_ITEM);
	}

	/**
	 * Adds an Item to the RenderInstance and renders it on the world.
	 * If there is no instance of the Item, create a new instance.
	 * 
	 * @param i Item to render
	 * @param position where the Item should be rendered
	 */
	public void addItemInstance(Item i, Transform position) {
		InstancedImage actor;
		
		if(!_renderMap.containsKey(i)) {
		    ItemType itemType    = i.getItemType();
		    Texture  itemTexture = new Texture(itemType.getTexturePath());
		    float    itemWidth   = itemType.getWidth();
		    float    itemHeight  = itemType.getHeight();

			actor = new InstancedImage.Builder()
											.texture( itemTexture )
											.size( itemWidth, itemHeight )
											.anchor(Anchor.BOTTOM)
											.sorted(Depth2DComparator)
											.build();
			addActor(actor);
			_renderMap.put(i.getItemType(), actor);
			
		} else {
			actor = _renderMap.get(i);
		}
		
		actor.addInstance(position);
	}
	
	/**
	 * Removes an item from the world.
	 * 
	 * @param i the Item to remove
	 * @param position where the Item is located
	 */
	public void removeItemInstance(Item i, Transform position) {
	    if(!_renderMap.containsKey(i)) {
	        return;
	    }
 
	    InstancedImage actor = _renderMap.get(i);
	    actor.removeInstance(position);
	}

	private Map<ItemType, InstancedImage> _renderMap = new HashMap<>();
	
    private Comparator<Transform> Depth2DComparator = new Comparator<Transform>() {
        @Override
        public int compare(Transform o1, Transform o2) {
            return Float.compare(o1.position.y, o2.position.y);
        }
    };
}
