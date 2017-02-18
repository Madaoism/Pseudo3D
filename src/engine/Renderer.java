package engine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import state.State;

public class Renderer {
	
	long window;

    public Renderer(long window) {        
    	this.window = window;
    }
    
    public void init() throws Exception {
		// Set the initial clear color
		glClearColor(1.0f, 0.5f, 0.0f, 0.0f);
    }

    public void clear() {
    	// Clear the screen with the clear color
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    
    public void randomColor() {
    	// Set the clear color randomly
		glClearColor((float) Math.random(), 
				(float) Math.random(),
				(float) Math.random(),
				(float) Math.random());
    }
    
    public void render(State gamestate) {
    	gamestate.render(this);
    	clear();
    }
}
