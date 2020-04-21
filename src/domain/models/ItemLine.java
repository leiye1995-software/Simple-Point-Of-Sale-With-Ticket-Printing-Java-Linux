package domain.models;

import domain.utils.ReceiptBuildingHelper;

public class ItemLine {

	private String productName;
	
	private float price;

	private int quantity;
	
	public ItemLine (String productName, float price, int quantity) {
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String getProductName () {
		return productName;
	}

	public float getPrice () {
		return price;
	}
	
	public int getQuantity () {
		return quantity;
	}
	
	public float getTotalPrice () {
		return quantity * price;
	}
	
	public void increaseQuantity (int quantity) {
		this.quantity += quantity;
	}
	
	public boolean decreaseQuantity (int quantity) {
		this.quantity -= quantity;
		return this.quantity == 0;
	}
	
	public String buildReceiptLine () {
		float total = getTotalPrice();
		String formattedTotalPrice = String.format(ReceiptBuildingHelper.PRICE_FORMAT, total);
		String formattedQuantityAndTotalPrice = String.format(ReceiptBuildingHelper.QUANTITY_FORMAT, price, quantity, getTotalPrice());
		String line1 = ReceiptBuildingHelper.build2Columns(productName, formattedTotalPrice);
		return line1 + ReceiptBuildingHelper.NEW_LINE + formattedQuantityAndTotalPrice;
	}
}
