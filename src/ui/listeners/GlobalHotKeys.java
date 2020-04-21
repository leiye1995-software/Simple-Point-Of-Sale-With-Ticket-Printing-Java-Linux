package ui.listeners;

import java.awt.event.KeyEvent;

import ui.PosUiBuilder;

public class GlobalHotKeys {
	
	public static void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_HOME:
				PosUiBuilder.focusOnCustomPrice();
				break;
			case KeyEvent.VK_END:
				PosUiBuilder.focusOnPayment();
				break;
			case KeyEvent.VK_PAGE_UP:
				PosUiBuilder.checkoutOnly();
				break;
			case KeyEvent.VK_PAGE_DOWN:
				PosUiBuilder.checkoutPrint();
				break;
			case KeyEvent.VK_RIGHT:
				PosUiBuilder.cancel();
				break;
		}
	}
}
