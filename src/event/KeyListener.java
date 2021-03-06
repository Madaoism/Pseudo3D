package event;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;

import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class KeyListener {

	private long window;
	private boolean isPressed;
	private double pressTimer = 500;
	private double start = 0;
	GLFWWindowSizeCallback windowSizeCallback;
	
	public static final int KEY_ENTER = GLFW_KEY_ENTER;
	
	public KeyListener(long window) { 
		this.window = window;
	}
	
	// Check if a key is being pressed down currently
	public boolean isKeyHeld( int keyCode ){ 
		return glfwGetKey(window, keyCode) == GLFW_PRESS;
	}
	
	// Check if a key is being deliberately pressed down
	public boolean isKeyPressed( int keyCode) {
		
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
		else {
			isPressed = false;
		}
		
		return false;
	}
	
	// Get current time in milliseconds
	private double getTime() {
		return System.currentTimeMillis();
	}
}
