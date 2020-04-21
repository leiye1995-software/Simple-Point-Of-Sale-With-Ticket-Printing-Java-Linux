package ui.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SaleHistoryUiInfoManager {

	private ArrayList<String> histories;

	public static final String NEW_LINE = System.getProperty("line.separator");
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
	
	public SaleHistoryUiInfoManager () {
		histories = new ArrayList<>();
	}
	
	public void addSale (Date date, String total) {
		histories.add(simpleDateFormat.format(date) + "  " + total);
	}
	
	public String getHistoryInfo () {
		StringBuilder result = new StringBuilder();
		histories.forEach(history -> {
			result.append(history + NEW_LINE);
		});
		return result.toString();
	}
}
