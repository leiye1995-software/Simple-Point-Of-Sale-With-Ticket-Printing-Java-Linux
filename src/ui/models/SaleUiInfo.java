package ui.models;

import java.util.ArrayList;

public class SaleUiInfo {

	private ArrayList<ItemLineUiInfo> itemLines;
	private String total;
	
	public SaleUiInfo () {
		itemLines = new ArrayList<>();
		total = "0.00";
	}
	
	public void addItemLine (String productName, String quantity, String price) {
		itemLines.add(new ItemLineUiInfo(productName, quantity, price));
	}
	
	public ArrayList<ItemLineUiInfo> getItemLines () {
		return itemLines;
	}

	public String getTotal () {
		return total;
	}
	
	public void setTotal (String total) {
		this.total = total;
	}
}
