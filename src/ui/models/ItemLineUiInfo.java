package ui.models;

public class ItemLineUiInfo {
	
	private String productName;
	private String quantity;
	private String price;
	
	public ItemLineUiInfo(String productName, String quantity, String price) {
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	public String getPrice() {
		return price;
	}
}
