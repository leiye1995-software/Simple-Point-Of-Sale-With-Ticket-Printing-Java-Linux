package domain.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class EscPosInterface {

	private static EscPosInterface instance = null;
	
	private String printerDevicePath = null;
	
	private static final byte[] INIT = {
			0x1b,
			0x40
	};
	
	private static final byte[] END = {
			0x0a,
			0x1d,
			0x56,
			0x41,
			0x03
	};
	
	private EscPosInterface () {
	}
	
	public static EscPosInterface getInstance () {
		if (instance == null) {
			instance = new EscPosInterface();
		}
		return instance;
	}
	
	public void setPrinterDevicePath (String path) {
		printerDevicePath = path;
		if (printerDevicePath != null) {
			printerDevicePath = printerDevicePath.trim();
		}
	}
	
	public void printReceipt (String receipt) {
		if (printerDevicePath != null && !printerDevicePath.isEmpty()) {
			try {
				Files.write(Paths.get(printerDevicePath), concatenateByteArrays(INIT, receipt.getBytes(), END), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private byte[] concatenateByteArrays (byte[] ... byteArrays) {
		int length = 0;
		for (byte[] byteArray : byteArrays) {
			length += byteArray.length;
		}
		byte[] result = new byte[length];
		int i = 0;
		for (byte[] byteArray : byteArrays) {
			for (byte byteData : byteArray) {
				result[i] = byteData;
				++i;
			}
		}
		return result;
	}
}
