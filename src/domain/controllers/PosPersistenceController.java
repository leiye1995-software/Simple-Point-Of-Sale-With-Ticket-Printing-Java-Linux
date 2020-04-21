package domain.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;

import domain.models.Sale;

public class PosPersistenceController {

	private static PosPersistenceController instance = null;
	private static final char SLASH = '/';
	private static final String EXTENSION = ".txt";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private String storagePath = null;
	
	public static PosPersistenceController getInstance () {
		if (instance == null) {
			instance = new PosPersistenceController();
		}
		return instance;
	}
	
	public void setStoragePath (String path) {
		if (!"no".equals(path)) {
			if (path.charAt(path.length()-1) != SLASH) {
				path = path + SLASH;
			}
			storagePath = path;
		}
	}
	
	public void saveSale (Sale sale) {
		if (storagePath != null) {
			try {
				Files.write(Paths.get(storagePath + simpleDateFormat.format(sale.getCheckoutDate()) + EXTENSION), sale.getReceipt().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
