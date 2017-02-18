package game;

import engine.GameEngine;

// Code source at https://www.lwjgl.org/guide
// GLFW is an open source library for OpenGL, OpenGL ES and Vulkan
// It provides a simple API for creating windows, contexts and surfaces

public class windows_glfw {

	private static GameEngine game;
	private static int width = 800;
	private static int height = 600;
	private static String gameTitle = "Pseudo3D Game Demo";

	public static void main(String[] args) {
		try {
			game = new GameEngine(width, height, gameTitle);
			game.run();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
