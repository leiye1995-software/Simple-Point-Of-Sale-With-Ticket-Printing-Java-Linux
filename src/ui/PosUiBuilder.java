package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import domain.controllers.PosSaleController;
import ui.listeners.TextFieldFocusListener;
import ui.listeners.CustomPriceKeyListener;
import ui.listeners.DefaultKeyListener;
import ui.listeners.PaymentKeyListener;
import ui.models.ItemLineUiInfo;
import ui.models.SaleUiInfo;
import ui.utils.PosUiConstants;
import ui.utils.PosUiProductType;

public class PosUiBuilder {
	
	private static JButton deleteItemLineButton;
	private static JButton deleteAllItemLineButton;
	private static JButton checkoutOnlyButton;
	private static JButton checkoutPrintButton;
	private static JButton cancelSaleButton;
	private static JButton exitButton;
	private static JLabel totalLabelNumber;
	private static JLabel changeLabelNumber;
	private static JTable saleInfoTable;
	private static JTextField customPrice;
	private static JTextField payment;
	private static JTextArea historyTextArea;
	
	//private static ArrayList<PriceButton> priceButtons;
	
	private static DefaultTableModel saleInfoTableModel;
	
	
	public static void createAndShowGUI () {
		
		JFrame frame = new JFrame(PosUiConstants.POS_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponents(frame.getContentPane());
		addListeners(frame);
		frame.setFocusable(true);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setMinimumSize(new Dimension(300, 300));
		frame.setVisible(true);
	}
	
	

	public static void checkoutOnly() {
		cleanInputFields();
		boolean updated = PosSaleController.getInstance().checkoutOnly();
		if (updated) {
			updateSaleUiInfo();
		}
		else {
			updateChange();
		}
	}

	public static void checkoutPrint() {
		cleanInputFields();
		boolean updated = PosSaleController.getInstance().checkoutPrint();
		if (updated) {
			updateSaleUiInfo();
		}
		else {
			updateChange();
		}
	}

	public static void cancel() {
		cleanInputFields();
		updateChange();
		boolean updated = PosSaleController.getInstance().cancelSale();
		if (updated) {
			updateSaleUiInfo();
		}
		else {
			updateChange();
		}
	}
	
	public static void updateSaleUiInfo () {
		SaleUiInfo saleUiInfo = PosSaleController.getInstance().getSaleUiInfo();
		
		String[][] lines;
		int itemLinesSize = saleUiInfo.getItemLines().size();
		if (itemLinesSize > 0) {
			lines = new String[itemLinesSize][];
			for (int i = 0; i < saleUiInfo.getItemLines().size(); ++i) {
				ItemLineUiInfo itemLine = saleUiInfo.getItemLines().get(i);
				lines[i] = new String[] {itemLine.getProductName(), itemLine.getQuantity(), itemLine.getPrice()};
			}
		}
		else {
			lines = PosUiConstants.INITIAL_TABLE_VALUES;
		}
		saleInfoTableModel.setDataVector(lines, PosUiConstants.COLUMN_NAMES);
		historyTextArea.setText(PosSaleController.getInstance().getSaleHistory().getHistoryInfo());
		totalLabelNumber.setText(saleUiInfo.getTotal());
		updateChange();
	}
	
	public static void updateChange() {
		changeLabelNumber.setText(PosSaleController.getInstance().getChange());
	}
	
	public static void focusOnCustomPrice () {
		customPrice.requestFocusInWindow();
	}
	
	public static void focusOnPayment () {
		payment.requestFocusInWindow();
	}
	
	private static void addComponents (Container mainPane) {

		mainPane.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_DEFAULT;

		gridBagConstraints.weightx = PosUiConstants.WEIGHT_X_PRICE_SELECTION;
		mainPane.add(buildPriceSelectionPanel(), gridBagConstraints);
		gridBagConstraints.weightx = PosUiConstants.WEIGHT_X_SALE_INFO;
		mainPane.add(buildSaleInfoPanel(), gridBagConstraints);
		gridBagConstraints.weightx = PosUiConstants.WEIGHT_X_SALE_RESULT;
		mainPane.add(buildSaleResultPanel(), gridBagConstraints);
	}

	
	private static void addListeners (JFrame frame) {
		frame.addKeyListener(new DefaultKeyListener());
		
		/*priceButtons.forEach(button -> {
			button.addActionListener(actionEvent -> {
				PosSaleController.getInstance().addItem(PosUiProductType.VARIED.getReceiptValue(), button.getPriceType().getPriceValue(), 1);
				updateSaleUiInfo();
			});
		});*/

		customPrice.addActionListener(actionEvent -> {
			PosSaleController.getInstance().addItemCustomPrice(PosUiProductType.VARIED.getReceiptValue(), customPrice.getText());
			customPrice.setText(PosUiConstants.EMPTY);
			updateSaleUiInfo();
		});
		
		customPrice.addKeyListener(new CustomPriceKeyListener(customPrice));
		
		customPrice.addFocusListener(new TextFieldFocusListener(customPrice, PosUiConstants.COLOR_DEFAULT));
		
		saleInfoTable.addKeyListener(new DefaultKeyListener());
		
		deleteItemLineButton.addActionListener(actionEvent -> {
			int selectedRow = saleInfoTable.getSelectedRow();
			if (selectedRow != -1 && saleInfoTableModel.getRowCount() > 0) {
				PosSaleController.getInstance().deleteItem(
						(String)saleInfoTableModel.getValueAt(selectedRow, PosUiConstants.TABLE_POSITION_PRODUCT_NAME),
						PosUiConstants.VALUE_QUANTITY_DEFAULT,
						(String)saleInfoTableModel.getValueAt(selectedRow, PosUiConstants.TABLE_POSITION_PRICE));
				updateSaleUiInfo();
			}
		});
		
		deleteAllItemLineButton.addActionListener(actionEvent -> {
			int selectedRow = saleInfoTable.getSelectedRow();
			if (selectedRow != -1 && saleInfoTableModel.getRowCount() > 0) {
				PosSaleController.getInstance().deleteItem(
						(String)saleInfoTableModel.getValueAt(selectedRow, PosUiConstants.TABLE_POSITION_PRODUCT_NAME),
						(String)saleInfoTableModel.getValueAt(selectedRow, PosUiConstants.TABLE_POSITION_QUANTITY),
						(String)saleInfoTableModel.getValueAt(selectedRow, PosUiConstants.TABLE_POSITION_PRICE));
				updateSaleUiInfo();
			}
		});

		payment.addKeyListener(new PaymentKeyListener(payment));
		payment.addFocusListener(new TextFieldFocusListener(payment, PosUiConstants.COLOR_DEFAULT));
		
		checkoutOnlyButton.addActionListener(actionEvent -> {
			checkoutOnly();
		});
		
		checkoutPrintButton.addActionListener(actionEvent -> {
			checkoutPrint();
		});
		
		cancelSaleButton.addActionListener(actionEvent -> {
			cancel();
		});
		
		exitButton.addActionListener(actionEvent -> {
			System.exit(0);
		});
	}

	private static JPanel buildPriceSelectionPanel() {
		
		CompoundBorder compoundBorder = new CompoundBorder(
				new EmptyBorder(PosUiConstants.BORDER_PADDING, PosUiConstants.BORDER_PADDING, PosUiConstants.BORDER_PADDING, PosUiConstants.BORDER_PADDING),
				BorderFactory.createLineBorder(Color.black));
		
		JPanel priceSelectionColumn = new JPanel(new GridBagLayout());
		priceSelectionColumn.setBorder(compoundBorder);
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = PosUiConstants.WEIGHT_X_DEFAULT;
		gridBagConstraints.gridx = PosUiConstants.GRID_X_DEFAULT;
		
		
		/*gridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_PRICE_BUTTON;
		priceButtons = new ArrayList<>();
		for (PosUiPriceType priceType : PosUiPriceType.values()) {
			PriceButton button = (PriceButton) setFontSize(new PriceButton(priceType), null, Font.BOLD, PosUiConstants.TEXT_SIZE_BUTTON_DEFAULT);
			priceSelectionColumn.add(button, gridBagConstraints);
			priceButtons.add(button);
		}*/
		
		JLabel customPriceLabel = (JLabel) setFontSize(new JLabel(PosUiConstants.LABEL_CUSTOM_PRICE),  PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_CUSTOM_PRICE_LABEL);
		customPriceLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		/*JLabel customPriceExampleLabel = (JLabel) setFontSize(new JLabel(PosUiConstants.LABEL_CUSTOM_PRICE_EXAMPLE),  PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_CUSTOM_PRICE_LABEL_EXAMPLE);
		customPriceExampleLabel.setVerticalAlignment(SwingConstants.BOTTOM);*/
		
		customPrice = (JTextField) setFontSize(new JTextField(), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_CUSTOM_PRICE);

		JLabel historyLabel = (JLabel) setFontSize(new JLabel(PosUiConstants.LABEL_HISTORY),  PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_HISTORY);
		historyLabel.setVerticalAlignment(SwingConstants.BOTTOM);

		historyTextArea = (JTextArea) setFontSize(new JTextArea(),  PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_HISTORY);
		historyTextArea.setEditable(false);
		
		gridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_CUSTOM_PRICE_LABEL;
		priceSelectionColumn.add(customPriceLabel, gridBagConstraints);
		//priceSelectionColumn.add(customPriceExampleLabel, gridBagConstraints);
		gridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_CUSTOM_PRICE_TEXT_FIELD;
		priceSelectionColumn.add(customPrice, gridBagConstraints);
		gridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_HISTORY_LABEL;
		priceSelectionColumn.add(historyLabel, gridBagConstraints);
		gridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_HISTORY;
		priceSelectionColumn.add(historyTextArea, gridBagConstraints);
		
		return priceSelectionColumn;
	}

	private static JPanel buildSaleInfoPanel() {
		saleInfoTableModel = new DefaultTableModel(PosUiConstants.INITIAL_TABLE_VALUES,PosUiConstants.COLUMN_NAMES);
		saleInfoTable = (JTable) setFontSize(new JTable(saleInfoTableModel), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_SALE_INFO_TABLE);
		setFontSize(saleInfoTable.getTableHeader(), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_SALE_INFO_TABLE);
		saleInfoTable.setRowHeight(PosUiConstants.TABLE_ROW_HEIGHT);
		saleInfoTable.setFillsViewportHeight(true);
		
		JScrollPane saleInfoTableScrollPane = new JScrollPane(saleInfoTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		deleteItemLineButton = (JButton) setFontSize(new JButton(PosUiConstants.BUTTON_DELETE_ITEM_LINE), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_BUTTON_DEFAULT);

		deleteAllItemLineButton = (JButton) setFontSize(new JButton(PosUiConstants.BUTTON_DELETE_ALL_ITEM_LINE), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_BUTTON_DEFAULT);
		

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_DEFAULT;
		gridBagConstraints.weightx = PosUiConstants.WEIGHT_X_DEFAULT;

		JPanel deleteButtonsPanel = new JPanel(new GridBagLayout());
		deleteButtonsPanel.add(deleteItemLineButton, gridBagConstraints);
		deleteButtonsPanel.add(deleteAllItemLineButton, gridBagConstraints);


		gridBagConstraints.gridx = PosUiConstants.GRID_X_DEFAULT;

		CompoundBorder compoundBorder = new CompoundBorder(
				new EmptyBorder(PosUiConstants.BORDER_PADDING, PosUiConstants.BORDER_PADDING, PosUiConstants.BORDER_PADDING, PosUiConstants.BORDER_PADDING),
				BorderFactory.createLineBorder(Color.black));
		
		JPanel saleInfoPanel = new JPanel(new GridBagLayout());
		saleInfoPanel.setBorder(compoundBorder);
		
		gridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_SALE_INFO_TABLE;
		saleInfoPanel.add(saleInfoTableScrollPane, gridBagConstraints);
		gridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_DELETE_BUTTONS;
		saleInfoPanel.add(deleteButtonsPanel, gridBagConstraints);
		
		return saleInfoPanel;
	}

	private static JPanel buildSaleResultPanel() {

		JLabel totalLabel = (JLabel) setFontSize(new JLabel(PosUiConstants.LABEL_TOTAL), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_TOTAL);
		
		totalLabelNumber = (JLabel) setFontSize(new JLabel(PosUiConstants.LABEL_DEFAULT_TOTAL_VALUE), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_TOTAL_NUMBER);
		totalLabelNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		
		GridBagConstraints totalGridBagConstraints = new GridBagConstraints();
		totalGridBagConstraints.fill = GridBagConstraints.BOTH;
		totalGridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_DEFAULT;

		JPanel totalPanel = new JPanel(new GridBagLayout());
		totalGridBagConstraints.weightx = PosUiConstants.WEIGHT_X_TOTAL_LABEL;
		totalPanel.add(totalLabel, totalGridBagConstraints);
		totalGridBagConstraints.weightx = PosUiConstants.WEIGHT_X_TOTAL_LABEL_NUMBER;
		totalPanel.add(totalLabelNumber, totalGridBagConstraints);

		GridBagConstraints paymentGridBagConstraints = new GridBagConstraints();
		paymentGridBagConstraints.fill = GridBagConstraints.BOTH;
		paymentGridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_DEFAULT;
		
		JLabel paymentLabel = (JLabel) setFontSize(new JLabel(PosUiConstants.LABEL_PAYMENT), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_PAYMENT_LABEL);
		payment = (JTextField) setFontSize(new JTextField(), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_PAYMENT);
		
		JPanel paymentPanel = new JPanel(new GridBagLayout());
		paymentGridBagConstraints.weightx = PosUiConstants.WEIGHT_X_PAYMENT_LABEL;
		paymentPanel.add(paymentLabel, paymentGridBagConstraints);
		paymentGridBagConstraints.weightx = PosUiConstants.WEIGHT_X_PAYMENT_TEXT_FIELD;
		paymentPanel.add(payment, paymentGridBagConstraints);
		

		GridBagConstraints changeGridBagConstraints = new GridBagConstraints();
		changeGridBagConstraints.fill = GridBagConstraints.BOTH;
		changeGridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_DEFAULT;
		
		JLabel changeLabel = (JLabel) setFontSize(new JLabel(PosUiConstants.LABEL_CHANGE), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_CHANGE_LABEL);
		changeLabelNumber = (JLabel) setFontSize(new JLabel(PosUiConstants.LABEL_DEFAULT_TOTAL_VALUE), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_PAYMENT);
		
		JPanel changePanel = new JPanel(new GridBagLayout());
		changeGridBagConstraints.weightx = PosUiConstants.WEIGHT_X_CHANGE_LABEL;
		changePanel.add(changeLabel, changeGridBagConstraints);
		changeGridBagConstraints.weightx = PosUiConstants.WEIGHT_X_CHANGE_LABEL_NUMBER;
		changePanel.add(changeLabelNumber, changeGridBagConstraints);
		
		checkoutOnlyButton = (JButton) setFontSize(new JButton(PosUiConstants.BUTTON_CHECKOUT_ONLY), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_BUTTON_DEFAULT);
		
		checkoutPrintButton = (JButton) setFontSize(new JButton(PosUiConstants.BUTTON_CHECKOUT_PRINT), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_BUTTON_DEFAULT);
		
		cancelSaleButton = (JButton) setFontSize(new JButton(PosUiConstants.BUTTON_CANCEL_SALE), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_BUTTON_DEFAULT);
		
		exitButton = (JButton) setFontSize(new JButton(PosUiConstants.BUTTON_EXIT), PosUiConstants.NAME_DEFAULT, Font.BOLD, PosUiConstants.TEXT_SIZE_BUTTON_DEFAULT);
		

		CompoundBorder compoundBorder = new CompoundBorder(
				new EmptyBorder(PosUiConstants.BORDER_PADDING, PosUiConstants.BORDER_PADDING, PosUiConstants.BORDER_PADDING, PosUiConstants.BORDER_PADDING),
				BorderFactory.createLineBorder(Color.black));
		
		JPanel saleResult = new JPanel(new GridBagLayout());
		saleResult.setBorder(compoundBorder);

		GridBagConstraints resultGridBagConstraints = new GridBagConstraints();
		resultGridBagConstraints.fill = GridBagConstraints.BOTH;
		resultGridBagConstraints.weightx = PosUiConstants.WEIGHT_X_DEFAULT;
		resultGridBagConstraints.gridx = PosUiConstants.GRID_X_DEFAULT;
		
		resultGridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_TOTAL_PANEL;
		saleResult.add(totalPanel, resultGridBagConstraints);
		resultGridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_PAYMENT_PANEL;
		saleResult.add(paymentPanel, resultGridBagConstraints);
		resultGridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_CHANGE_PANEL;
		saleResult.add(changePanel, resultGridBagConstraints);
		resultGridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_CHECKOUT_ONLY_BUTTON;
		saleResult.add(checkoutOnlyButton, resultGridBagConstraints);
		resultGridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_CHECKOUT_PRINT_BUTTON;
		saleResult.add(checkoutPrintButton, resultGridBagConstraints);
		resultGridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_CANCEL_SALE_BUTTON;
		saleResult.add(cancelSaleButton, resultGridBagConstraints);
		resultGridBagConstraints.weighty = PosUiConstants.WEIGHT_Y_EXIT;
		saleResult.add(exitButton, resultGridBagConstraints);
		return saleResult;
	}
	
	private static void cleanInputFields() {
		customPrice.setText(PosUiConstants.EMPTY);
		payment.setText(PosUiConstants.EMPTY);
	}
	
	private static Component setFontSize (Component component, String name, Integer style, Integer size) {
		Font originalFont = component.getFont();
		String nameToApply = (String) ifOneNullGetOther(name, originalFont.getFontName());
		Integer styleToApply = (Integer) ifOneNullGetOther(style, originalFont.getStyle());
		Integer sizeToApply = (Integer) ifOneNullGetOther(size, originalFont.getSize());
		
		component.setFont(new Font(nameToApply, styleToApply.intValue(), sizeToApply.intValue()));
		return component;
	}
	
	private static Object ifOneNullGetOther (Object one, Object other) {
		if (one == null) {
			return other;
		}
		return one;
	}
}
