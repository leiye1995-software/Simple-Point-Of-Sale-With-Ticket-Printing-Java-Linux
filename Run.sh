#!/bin/bash
LATEST_JAR_FILE_PATH="$(ls -r $HOME/Documents/*.jar | head -n 1)"
PRINTER_PATH=/dev/usb/lp0
STORAGE=$HOME/Documents/PosStorage
java -jar $LATEST_JAR_FILE_PATH $PRINTER_PATH $STORAGE