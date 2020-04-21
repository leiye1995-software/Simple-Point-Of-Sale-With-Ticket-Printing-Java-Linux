package domain.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptBuildingHelper {

	public static final String DIVIDER = "--------------------------------";
	public static final String RECEIPT_HEADER = String.format("          <shop name>%n     <shop address>%n     <shop address>%n     <shop data>%n     <shop contact>");
	public static final String RECEIPT_FOOTER = String.format("   <footer text>");
	public static final String SPACER = " ";
	public static final String NEW_LINE = System.getProperty("line.separator");
	public static final String TOTAL = "TOTAL";
	public static final String PAYMENT = "Paid";
	public static final String CHANGE = "Change";
	public static final String IVA = "IVA included";
	public static final String QUANTITY_FORMAT = "    %.2f X %d = %.2f";
	public static final String PRICE_FORMAT = "%.2f";
	public static final int RECEIPT_WIDTH = 32;
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	public static String buildHeader (Date date) {
		return DIVIDER + NEW_LINE + RECEIPT_HEADER + NEW_LINE + DIVIDER + NEW_LINE + simpleDateFormat.format(date) + NEW_LINE + NEW_LINE;
	}
	
	public static String buildFooter (float totalPrice, float payment, float change) {
		String totalPriceString = String.format(PRICE_FORMAT, totalPrice);
		String paymentString = String.format(PRICE_FORMAT, payment);
		String changeString = String.format(PRICE_FORMAT, change);
		String totalLine = build2Columns(TOTAL, totalPriceString);
		String paymentLine = build2Columns(PAYMENT, paymentString);
		String changeLine = build2Columns(CHANGE, changeString);
		return DIVIDER + NEW_LINE + totalLine + NEW_LINE + paymentLine + NEW_LINE + changeLine + NEW_LINE + IVA + NEW_LINE + NEW_LINE + RECEIPT_FOOTER;
	}
	
	public static String repeatString (String string, int repetitions) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < repetitions; ++i) {
			result.append(string);
		}
		return result.toString();
	}
	
	public static int getSpacers (int occupied) {
		return RECEIPT_WIDTH - occupied;
	}
	
	public static String build2Columns (String column1, String column2) {
		return column1 + repeatString(SPACER, getSpacers(column1.length() + column2.length())) + column2;
	}
}
