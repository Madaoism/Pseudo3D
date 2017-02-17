package game;

import engine.GameEngine;

// Code source at https://www.lwjgl.org/guide
// GLFW is an open source library for OpenGL, OpenGL ES and Vulkan
// It provides a simple API for creating windows, contexts and surfaces

public class windows_glfw {

	static GameEngine game;

	public static void main(String[] args) {
		try {
			game = new GameEngine();
			game.run();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
