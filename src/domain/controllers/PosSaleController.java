package domain.controllers;

import java.util.ArrayList;

import domain.models.Sale;
import domain.utils.PosDomainConstants;
import ui.models.SaleHistoryUiInfoManager;
import ui.models.SaleUiInfo;
import ui.utils.PosUiConstants;

public class PosSaleController {
	
	private static Sale sale = null;
	private static PosSaleController instance = null;
	private static ArrayList<Sale> saleHistory = null;
	
	private PosSaleController () {
		newSale();
		saleHistory = new ArrayList<>();
	}
	
	public static PosSaleController getInstance() {
		if (instance == null) {
			instance = new PosSaleController();
		}
		return instance;
	}

	public void newSale () {
		sale = new Sale();
	}
	
	public boolean cancelSale () {
		boolean checkoutable = sale.checkout();
		if (checkoutable) {
			newSale();
		}
		return checkoutable;
	}

	public void addItem (String productName, float price, int quantity) {
		sale.addItem(productName, price, quantity);
	}
	
	public void deleteItem (String productName, String quantity, String price) {
		float priceNumeric = getFloat(price);
		int quantityNumeric = getInteger(quantity);
		if (priceNumeric > 0.0f && quantityNumeric > 0) {
			sale.deleteItem(productName, quantityNumeric, priceNumeric);
		}
	}
	
	public float getTotal () {
		return sale.getTotalPrice();
	}
	
	public boolean checkoutOnly () {
		boolean checkoutable = sale.checkout();
		if (checkoutable) {
			addSaleToHistory();
			PosPersistenceController.getInstance().saveSale(sale);
			newSale();
		}
		return checkoutable;
	}
	
	public boolean checkoutPrint () {
		boolean checkoutable = sale.checkout();
		if (checkoutable) {
			addSaleToHistory();
			EscPosInterface.getInstance().printReceipt(sale.getReceipt());
			PosPersistenceController.getInstance().saveSale(sale);
			newSale();
		}
		return checkoutable;
	}
	
	public SaleUiInfo getSaleUiInfo () {
		SaleUiInfo saleUiInfo = new SaleUiInfo();
		sale.getItemLines().forEach(itemLine -> {
			saleUiInfo.addItemLine(itemLine.getProductName(), String.valueOf(itemLine.getQuantity()), String.format(PosUiConstants.PRICE_FORMAT, itemLine.getPrice()));
		});
		saleUiInfo.setTotal(String.format(PosUiConstants.LABEL_PRICE_FORMAT, getTotal()));
		return saleUiInfo;
	}
	
	public void addItemCustomPrice (String productName, String customPrice) {
		String[] priceElements = customPrice.split(PosDomainConstants.REGEX_MULTIPLICATION);
		if (priceElements.length > 2) {
			return;
		}
		float price = 0.0f;
		int quantity = 1;
		if (priceElements.length == 1) {
			price = getFloat(priceElements[0]);
		}
		else if (!priceElements[0].contains(PosDomainConstants.DOT)) {
			try {
				quantity = getInteger(priceElements[0]);
				price = getFloat(priceElements[1]);
			}
			catch (NumberFormatException e) {
				quantity = 0;
			}
		}
		else {
			return;
		}
		if (quantity > 0 && price > 0.0f) {
			addItem(productName, price, quantity);
		}
	}
	
	public void updatePayment (String payment) {
		sale.setPayment(getFloat(payment));
	}

	public String getPayment () {
		return String.format(PosUiConstants.LABEL_PRICE_FORMAT, sale.getPayment());
	}
	
	public String getChange () {
		return String.format(PosUiConstants.LABEL_PRICE_FORMAT, sale.getChange());
	}
	
	public SaleHistoryUiInfoManager getSaleHistory () {
		SaleHistoryUiInfoManager saleHistoryUiInfoManager = new SaleHistoryUiInfoManager();
		saleHistory.forEach(sale -> {
			saleHistoryUiInfoManager.addSale(sale.getCheckoutDate(), String.format(PosUiConstants.PRICE_FORMAT, sale.getTotalPrice()));
		});
		return saleHistoryUiInfoManager;
	}
	
	private static float getFloat (String floatText) {
		float floatNumeric;
		try {
			floatNumeric = Float.valueOf(floatText);
		}
		catch (NumberFormatException e) {
			floatNumeric = 0.0f;
		}
		return floatNumeric;
	}
	
	private static int getInteger (String integerText) {
		int integerNumeric;
		try {
			integerNumeric = Integer.valueOf(integerText);
		}
		catch (NumberFormatException e) {
			integerNumeric = 0;
		}
		return integerNumeric;
	}
	
	private static void addSaleToHistory() {
		saleHistory.add(0, sale);
		if (saleHistory.size() > PosDomainConstants.MAX_NUMBER_OF_SALES_HISTORY) {
			for (int i = PosDomainConstants.MAX_NUMBER_OF_SALES_HISTORY; i < saleHistory.size(); ++i) {
				saleHistory.remove(i);
			}
		}
	}
}
