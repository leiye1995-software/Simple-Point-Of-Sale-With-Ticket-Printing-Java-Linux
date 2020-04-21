package ui.listeners;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import ui.utils.PosUiConstants;

public class TextFieldFocusListener implements FocusListener {

	private JTextField textField;
	private Color focusColor;
	private Color originalColor;
	
	public TextFieldFocusListener(JTextField textField, Color focusColor) {
		this.textField = textField;
		this.focusColor = focusColor;
		this.originalColor = textField.getBackground();
	}
	@Override
	public void focusGained(FocusEvent e) {
		textField.setText(PosUiConstants.EMPTY);
		textField.setBackground(focusColor);
	}

	@Override
	public void focusLost(FocusEvent e) {
		textField.setText(PosUiConstants.EMPTY);
		textField.setBackground(originalColor);
	}

}
