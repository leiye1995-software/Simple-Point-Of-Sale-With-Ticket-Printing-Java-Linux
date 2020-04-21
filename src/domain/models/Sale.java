package domain.models;

import java.util.ArrayList;
import java.util.Date;

import domain.utils.ReceiptBuildingHelper;

public class Sale {

	private ArrayList<ItemLine> itemLines;
	private float payment;
	private Date checkoutDate;
	private String receipt;
	
	public Sale () {
		itemLines = new ArrayList<>();
		payment = 0.0f;
		checkoutDate = null;
		receipt = null;
	}
	
	public ArrayList<ItemLine> getItemLines () {
		return itemLines;
	}
	
	public void addItem (String productName, float price, int amount) {
		ItemLine itemLine = findItemLine(productName, price);
		if (itemLine != null) {
			itemLine.increaseQuantity(amount);
		}
		else {
			itemLines.add(new ItemLine(productName, price, amount));
		}
	}
	
	public void deleteItem (String productName, int quantity, float price) {
		ItemLine itemLine = findItemLine(productName, price);
		if (itemLine != null && itemLine.decreaseQuantity(quantity)) {
			itemLines.remove(itemLine);
		}
	}

	public float getTotalPrice () {
		float totalPrice = 0.0f;
		for (ItemLine itemLine : itemLines) {
			totalPrice += itemLine.getTotalPrice();
		}
		return totalPrice;
	}
	
	public String getReceipt () {
		if (this.receipt == null) {
			StringBuilder receipt = new StringBuilder(ReceiptBuildingHelper.buildHeader(checkoutDate));
			itemLines.forEach(itemLine -> {
				receipt.append(itemLine.buildReceiptLine() + ReceiptBuildingHelper.NEW_LINE);
			});
			receipt.append(ReceiptBuildingHelper.buildFooter(getTotalPrice(), payment, getChange()));
			this.receipt = receipt.toString();
		}
		return this.receipt;
	}
	
	private ItemLine findItemLine (String productName, float price) {
		ItemLine resultItemLine = null;
		int i = 0;
		boolean found = false;
		while (!found && i < itemLines.size()) {
			ItemLine itemLine = itemLines.get(i);
			if (itemLine.getProductName().equals(productName) && itemLine.getPrice() == price) {
				found = true;
				resultItemLine = itemLine;
			}
			else {
				++i;
			}
		}
		return resultItemLine;
	}
	
	public float getPayment() {
		return payment;
	}

	public void setPayment(float payment) {
		this.payment = payment;
	}
	
	public float getChange() {
		return Math.round((payment - getTotalPrice()) * 100.0f) / 100.0f;
	}
	
	public boolean isCheckoutable () {
		boolean checkoutable = true;
		if (itemLines.size() == 0) {
			checkoutable = false;
		}
		return checkoutable;
	}
	
	public boolean checkout () {
		boolean checkoutable = true;
		if (itemLines.size() > 0) {
			checkoutDate = new Date();
			float totalPrice = getTotalPrice();
			if (payment < totalPrice) {
				payment = totalPrice;
			}
		}
		else {
			checkoutable = false;
		}
		return checkoutable;
	}
	
	public Date getCheckoutDate () {
		return checkoutDate;
	}
}
