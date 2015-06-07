package de.orangestar.engine.input;

public class Mouse {

	private boolean _button0Pressed;
	private boolean _button1Pressed;

	public boolean getButton0() {
		return _button0Pressed;
	}

	public boolean getButton1() {
		return _button1Pressed;
	}

	public void onPress(int button) {
		if (button == 0) {
			_button0Pressed = true;
		} else if (button == 1) {
			_button1Pressed = true;
		} else {

		}
	}

	public void onRelease(int button) {
		if (button == 0) {
			_button0Pressed = false;
		} else if (button == 1) {
			_button1Pressed = false;
		} else {

		}
	}
	// static class MousePosition extends GLFWCursorPosCallback{
	// @Override
	// public void invoke(long window, double x, double y) {
	// System.out.println(x + ":x " + y + ":y");
	//
	// }
	// }
	// static class MouseButtonCallback extends GLFWMouseButtonCallback{
	//
	// @Override
	// public void invoke(long window, int button, int action, int mods) {
	// System.out.println("button: " + button);
	//
	// }
	//
	// }
	//
	// public MousePosition getMousePosition(){
	// return new MousePosition();
	// }
	//
	// public MouseButtonCallback getMouseButtonCallback(){
	// return new MouseButtonCallback();
	// }

}
