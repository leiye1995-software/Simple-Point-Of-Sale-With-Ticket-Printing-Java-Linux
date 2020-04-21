package ui.buttons;

import javax.swing.JButton;

import ui.utils.PosUiPriceType;

@SuppressWarnings("serial")
public class PriceButton extends JButton {

	private PosUiPriceType priceType;

	public PriceButton(PosUiPriceType priceType) {
		super(priceType.getUiValue());
		this.priceType = priceType;
	}
	
	public PosUiPriceType getPriceType () {
		return priceType;
	}
}
