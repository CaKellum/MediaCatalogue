package com.kellum.MovieCatalogue.logging;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class KellumLogger {

    public static KellumLogger shared = new KellumLogger();

    private Logger log;

    private KellumLogger() {
        this.log = LogManager.getRootLogger();
    }

    public void logUsage(String message) {
        this.log.info(message);
    }

    public void logError(String message, Exception error) {
        this.log.error(message + " " + error.getClass().getName() + " " + error.getMessage());
    }

    public void logDebug(String message) {
        this.log.debug("DEBUG: " + message);
    }
}
