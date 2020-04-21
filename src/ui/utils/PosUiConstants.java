package ui.utils;

import java.awt.Color;

public class PosUiConstants {

	public static final String POS_TITLE = "Point of sale";
	//public static final String LABEL_CUSTOM_PRICE = "Custom price";
	public static final String LABEL_CUSTOM_PRICE = "Price(1)";
	//public static final String LABEL_CUSTOM_PRICE_EXAMPLE = "(units*price + ENTER. Example: 2.4 | 2*.75 | 3*1.2 ):";
	public static final String LABEL_HISTORY = "History";
	public static final String LABEL_TOTAL = "Total:";
	public static final String LABEL_PRICE_FORMAT = "%.2f";
	public static final String LABEL_DEFAULT_TOTAL_VALUE = "0.00";
	public static final String LABEL_PAYMENT = "Payment(2):";
	public static final String LABEL_CHANGE = "Change:";
	public static final String COLUMN_PRODUCT = "Product";
	public static final String COLUMN_QUANTITY = "Quantity";
	public static final String COLUMN_PRICE = "Price";
	public static final String BUTTON_CHECKOUT_ONLY = "Checkout only(3)";
	public static final String BUTTON_CHECKOUT_PRINT = "Checkout print(4)";
	public static final String BUTTON_CANCEL_SALE = "Cancel sale(5)";
	public static final String BUTTON_EXIT = "Exit";
	public static final String BUTTON_DELETE_ITEM_LINE = "Delete one item line";
	public static final String BUTTON_DELETE_ALL_ITEM_LINE = "Delete all items";
	public static final String EMPTY = "";
	public static final String VALUE_QUANTITY_DEFAULT = "1";
	
	public static final String[] COLUMN_NAMES = {COLUMN_PRODUCT, COLUMN_QUANTITY, COLUMN_PRICE};
	public static final String[][] INITIAL_TABLE_VALUES = new String[][]{};
	
	public static final int WEIGHT_Y_DEFAULT = 1;
	public static final int WEIGHT_X_DEFAULT = 1;
	public static final int WEIGHT_X_PRICE_SELECTION = 10;
	public static final int WEIGHT_X_SALE_INFO = 10;
	public static final int WEIGHT_X_SALE_RESULT = 10;
	public static final int WEIGHT_X_TOTAL_LABEL = 1;
	public static final int WEIGHT_X_TOTAL_LABEL_NUMBER = 2;
	public static final int WEIGHT_X_PAYMENT_LABEL = 1;
	public static final int WEIGHT_X_PAYMENT_TEXT_FIELD = 2;
	public static final int WEIGHT_X_CHANGE_LABEL = 1;
	public static final int WEIGHT_X_CHANGE_LABEL_NUMBER = 2;
	public static final int WEIGHT_Y_PRICE_BUTTON = 3;
	public static final int WEIGHT_Y_CUSTOM_PRICE_LABEL = 1;
	public static final int WEIGHT_Y_CUSTOM_PRICE_TEXT_FIELD = 5;
	public static final int WEIGHT_Y_HISTORY_LABEL = 1;
	public static final int WEIGHT_Y_HISTORY = 4;
	public static final int WEIGHT_Y_PRICE_BOTTOM_PADDING = 10;
	public static final int WEIGHT_Y_SALE_INFO_TABLE = 3;
	public static final int WEIGHT_Y_DELETE_BUTTONS = 1;
	public static final int WEIGHT_Y_TOTAL_PANEL = 5;
	public static final int WEIGHT_Y_PAYMENT_PANEL = 1;
	public static final int WEIGHT_Y_CHANGE_PANEL = 1;
	public static final int WEIGHT_Y_CHECKOUT_ONLY_BUTTON = 3;
	public static final int WEIGHT_Y_CHECKOUT_PRINT_BUTTON = 3;
	public static final int WEIGHT_Y_CANCEL_SALE_BUTTON = 2;
	public static final int WEIGHT_Y_EXIT = 1;
	public static final int GRID_X_DEFAULT = 0;
	public static final int GRID_Y_DEFAULT = 0;
	public static final int BORDER_PADDING = 10;
	public static final int TABLE_POSITION_PRODUCT_NAME = 0;
	public static final int TABLE_POSITION_QUANTITY = 1;
	public static final int TABLE_POSITION_PRICE = 2;
	public static final int TABLE_ROW_HEIGHT = 32;
	
	public static final char ADD_KEY = '+';

	//Font
	public static final int TEXT_SIZE_BUTTON_DEFAULT = 30;
	public static final int TEXT_SIZE_CUSTOM_PRICE_LABEL = 20;
	public static final int TEXT_SIZE_CUSTOM_PRICE_LABEL_EXAMPLE = 16;
	public static final int TEXT_SIZE_CUSTOM_PRICE = 40;
	public static final int TEXT_SIZE_HISTORY = 20;
	public static final int TEXT_SIZE_SALE_INFO_TABLE = 32;
	public static final int TEXT_SIZE_PAYMENT_LABEL = 30;
	public static final int TEXT_SIZE_PAYMENT = 40;
	public static final int TEXT_SIZE_CHANGE_LABEL = 30;
	public static final int TEXT_SIZE_CHANGE_LABEL_NUMBER = 40;
	public static final int TEXT_SIZE_TOTAL = 40;
	public static final int TEXT_SIZE_TOTAL_NUMBER = 72;
	
	public static final Color COLOR_DEFAULT = Color.GREEN;
	public static final String NAME_DEFAULT = "Dialog.plain";

	public static final String PRICE_FORMAT = "%.2f";
}
