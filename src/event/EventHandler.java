package event;

public class EventHandler {
	
	KeyListener keyListener;
	private boolean isEnterPressed = false;
	
	public EventHandler(long window) {
		keyListener = new KeyListener(window);
	}
	
	public void update() {
		isEnterPressed = keyListener.isKeyPressed(KeyListener.KEY_ENTER);
	}
	
	public boolean isEnterPressed() {
		return isEnterPressed;
	}

}
