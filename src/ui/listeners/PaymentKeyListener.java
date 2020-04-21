package ui.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import domain.controllers.PosSaleController;
import ui.PosUiBuilder;

public class PaymentKeyListener extends KeyAdapter {
	
	private JTextField payment;
	
	public PaymentKeyListener (JTextField payment) {
		this.payment = payment;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		GlobalHotKeys.keyReleased(e);
		PosSaleController.getInstance().updatePayment(payment.getText());
		PosUiBuilder.updateChange();
	}
}
