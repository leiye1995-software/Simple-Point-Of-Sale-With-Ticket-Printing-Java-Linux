package ui.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DefaultKeyListener extends KeyAdapter {
	
	@Override
	public void keyReleased(KeyEvent e) {
		GlobalHotKeys.keyReleased(e);
	}

}
