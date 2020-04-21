package ui.utils;

public enum PosUiProductType {

	TEXTILE ("Textile","Textile"),
	TOY ("Toy","Toy"),
	STATIONERY ("Stationery","Stationery"),
	TOOL ("Tool","Tool"),
	VARIED ("Varied","Varied");
	
	private String uiValue;
	private String receiptValue;
	
	private PosUiProductType (String uiValue, String receiptValue) {
		this.uiValue = uiValue;
		this.receiptValue = receiptValue;
	}

	public String getUiValue () {
		return uiValue;
	}

	public String getReceiptValue () {
		return receiptValue;
	}

}
