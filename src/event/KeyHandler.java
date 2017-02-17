package event;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.glClearColor;

public class KeyHandler {

	long window;
	
	public KeyHandler(long window) { 
		this.window = window;
	}
	
	public void update() {
		if (isKeyPressed(GLFW_KEY_ENTER)) {
			glClearColor((float) Math.random(), 
					(float) Math.random(),
					(float) Math.random(),
					(float) Math.random());
		}
	}
	
	private boolean isKeyPressed( int keyCode ){ 
		return glfwGetKey(window, keyCode) == GLFW_PRESS;
	}
}
