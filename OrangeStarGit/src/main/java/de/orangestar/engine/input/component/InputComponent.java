package de.orangestar.engine.input.component;

import de.orangestar.engine.Component;
import de.orangestar.engine.GameObject;

public abstract class InputComponent extends Component {

    public InputComponent(GameObject obj) {
		super(obj);
	}

	public abstract void onUpdate();
    
}
