package main;

import javax.swing.SwingUtilities;

import domain.controllers.PosPersistenceController;
import domain.controllers.EscPosInterface;
import ui.PosUiBuilder;

public class Main {
    public static void main ( String[] args ) {
    	if (args.length == 2) {
    		EscPosInterface.getInstance().setPrinterDevicePath(args[0]);
    		PosPersistenceController.getInstance().setStoragePath(args[1]);
        	SwingUtilities.invokeLater(PosUiBuilder::createAndShowGUI);
    	}
    	else {
    		write("Add printer device path and storage path to program arguments");
    		write("Current program arguments:");
    		if (args.length == 0) {
    			write("No program arguments found");
    		}
    		else {
    			for (String arg : args) {
    				write(arg);
    			}
    		}
    	}
    }

	private static void write(String text) {
		System.out.println(text);
	}
    
}
