package engine;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import event.EventHandler;
import state.State;
import state.StateGame;

public class GameEngine {

	private boolean vsyncOn = false;
	
	private Renderer renderer;
	private TimeManager timer;
	private State stateGame;
	private State currentState;
	private EventHandler eventHandler;
	
	private long window;
	private int width, height;
	private String gameTitle;
	
	public GameEngine(int width, int height, String gameTitle, boolean vsyncOn) {
		this.width = width;
		this.height = height;
		this.gameTitle = gameTitle;
		this.vsyncOn = vsyncOn;
	}
	
	// Main function calls this function. Second most outer tier.
	public void run() throws Exception{
		System.out.println("Hello LWJGL" + Version.getVersion() + "!");
		
		// main program logics are here
		init();
		loop();
		
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	// Game Initialization
	public void init() throws Exception{
		
		// All necessary calls to glfw functions to boot the game
		init_glfw();
		
		// Initialize necessary game objects
		eventHandler = new EventHandler(window);
		stateGame = new StateGame(window, eventHandler);
		timer = new TimeManager(window);
		renderer = new Renderer(window);
		renderer.init();
		currentState = stateGame;
	}
	
	// Game Main Loop. Keep all components updated
	public void loop(){

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			
			timer.update();
			glfwSwapBuffers(window); // swap the color buffers
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();			
			
			eventHandler.update();
			timer.statUpdate(currentState);
			renderer.render(currentState);
			timer.sync();
		}
	}
	
	// Don't really need to understand this part for now
	public void init_glfw() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err
		GLFWErrorCallback.createPrint(System.err).set();
		
		// Initialize GLFW. 
		// Most GLFW functions will not work before doing this
		if ( !glfwInit() ) {
			throw new IllegalStateException("Unable to Initialize GLFW");
		}
		
		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		
		// the window will stay hidden after creation. The window will become visible after its ready
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); 
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		
		// Make windows compatible with OSX
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		// Create the window
		window = glfwCreateWindow(width, height, gameTitle, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		
		// Enable v-sync
		if ( vsyncOn ) {
			glfwSwapInterval(1);
		}

		// Make the window visible
		glfwShowWindow(window);
		
		
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		
	}
	
}
