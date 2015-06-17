package de.orangestar.engine.logic.component;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.values.Transform;

public class UnitLogicComponent extends LogicComponent {
	
	public UnitLogicComponent(GameObject obj) {
		super(obj);
	}

	@Override
	public void onUpdate() {
		getGameObject().setGlobalLastTransform(Transform.duplicate(getGameObject().getGlobalTransform()));
	}

}
