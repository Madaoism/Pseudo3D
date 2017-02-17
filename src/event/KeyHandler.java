package event;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class KeyHandler {

	private long window;
	private boolean isPressed;
	private double pressTimer = 500;
	private double start = 0;
	GLFWWindowSizeCallback windowSizeCallback;
	
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
	
	// Check if a key is being pressed down currently
	private boolean isKeyHeld( int keyCode ){ 
		return glfwGetKey(window, keyCode) == GLFW_PRESS;
	}
	
	// Check if a key is being deliberately pressed down
	private boolean isKeyPressed( int keyCode) {
		
		// temp for checking if key is being held down continuously
		boolean temp = isPressed;
		isPressed = isKeyHeld( keyCode );
		
		// pressing down for the first time
		if (temp == false && isPressed == true) {
			start = getTime();
			return true;
		}
		
		// continuously pressing down
		else if (temp == true && isPressed == true) {
			
			// pressTimer used as a threshold. If user press < pressTimer;
			// it won't trigger an event
			if ( getTime() - start < pressTimer) {
				return false;
			}
			else {
				return true;
			}
		}
		
		return false;
	}
	
	// Get current time in milliseconds
	private double getTime() {
		return System.currentTimeMillis();
	}
}
