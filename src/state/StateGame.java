package state;

import engine.Renderer;
import event.EventHandler;

public class StateGame extends State {
	
	private boolean changeColor;

	public StateGame(long window, EventHandler eventHandler) {
		super(window, eventHandler);
		state_ID = STATE_GAME;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		changeColor = eventHandler.isEnterPressed();
	}

	@Override
	public void saveState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endState() {
		// TODO Auto-generated method stub
		
	}
	
	public void render(Renderer renderer) {
		if (changeColor) {
			renderer.randomColor();
		}
	}

}
