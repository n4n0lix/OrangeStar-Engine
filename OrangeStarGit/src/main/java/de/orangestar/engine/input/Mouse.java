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

	public void setButton0(boolean b) {
		_button0Pressed = b;
	}

	public void setButton1(boolean b) {
		_button1Pressed = b;
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
