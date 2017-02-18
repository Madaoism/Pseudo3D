package state;

import engine.Renderer;
import event.EventHandler;

public abstract class State {
	
	protected int state_ID;
	protected long window;
	protected EventHandler eventHandler;
	
	public static final int STATE_GAME = 0x100;		// Game boot up state
	public static final int STATE_MAIN_MENU = 0x101;	// main menu state
	public static final int STATE_GAME_OVER = 0x102;	// game over state
	public static final int STATE_DUNGEON = 0x103;				// dungeon exploring state
	public static final int STATE_PAUSE_DUNGEON_MENU = 0x104;	// pause menu state in game
	public static final int STATE_BATTLE = 0X105;				// battle state
	public static final int STATE_PAUSE_BATTLE_MENU = 0x106;	// pause menu state in battle
	
	public State(long window, EventHandler eventHandler) {
		this.eventHandler = eventHandler;
		this.window = window;
	}
	
	public int getState_ID() {
		return state_ID;
	}
	
	abstract public void init();
	abstract public void update();
	abstract public void render(Renderer renderer);
	abstract public void saveState();
	abstract public void loadState();
	abstract public void endState();
}
