package engine;

import event.EventHandler;
import state.State;

// Tracks down the time spent in each loop and sync
public class TimeManager {
	
	private double start;
	private double prev = getTime();
	private double steps = 0.0;
	private double elapsed;
	private long currentFrameRate;
	private long window;
	
	private double fps = 60;	
	private double millisecPerFrame = 1000/fps;
	private double ups = 60;
	private double millisecPerUpdate = 1000/ups;
	
	public TimeManager(long window) {
		this.window = window;
	}
	
	// Get current time in milliseconds
	private double getTime() {
		return System.currentTimeMillis();
	}
	
	public long getWindow() {
		return window;
	}
	
	public void update() {
		// offset time
		start = getTime();
		elapsed = start - prev;
		currentFrameRate = (long) (1000/elapsed);
		prev = start;
		steps += elapsed;
	}
	
	public void statUpdate(State gamestate) {
		// handleInput();
		// sleep(start + millisecPerFrame - getTime());
		while (steps >= millisecPerUpdate) {
			gamestate.update();
			steps -= millisecPerUpdate;
		}
	}
	
	public void sync() {
		sync(start);
		System.out.println("fps: " + currentFrameRate);
	}
	
	// Sync frame rate with update rate
	public void sync(double loopStartTime) {
		double loopslot = millisecPerFrame;
		double endTime = loopStartTime + loopslot;
		while (getTime() < endTime) {
			try {
				Thread.sleep(1);
			} 
			catch (InterruptedException e) {
				Thread.currentThread().interrupt(); 
			}
		}
	}
	
	
}
