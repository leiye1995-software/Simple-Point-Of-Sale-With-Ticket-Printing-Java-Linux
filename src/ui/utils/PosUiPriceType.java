package ui.utils;

public enum PosUiPriceType {

	CENTS_60 ("Add one item 0.60",0.60f),
	CENTS_75 ("Add one item 0.75",0.75f),
	CENTS_85 ("Add one item 0.85",0.85f),
	CENTS_95 ("Add one item 0.95",0.95f);
	
	private String uiValue;
	private float priceValue;
	
	private PosUiPriceType (String uiValue, float priceValue) {
		this.uiValue = uiValue;
		this.priceValue = priceValue;
	}

	public String getUiValue () {
		return uiValue;
	}
	
	public float getPriceValue () {
		return priceValue;
	}
	
}
