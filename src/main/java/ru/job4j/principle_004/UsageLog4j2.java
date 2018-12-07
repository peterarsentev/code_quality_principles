package ru.job4j.principle_004;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Usage of log4j 2.
 */
public class UsageLog4j2 {

    /**
     * Log.
     */
    private static final Logger LOG = LogManager.getLogger(UsageLog4j2.class.getName());

    /**
     * Main.
     * @param args main.
     */
    public static void main(String[] args) {
        LOG.trace("trace message");
        LOG.debug("debug message");
        LOG.info("info message");
        LOG.warn("warn message");
        LOG.error("error message");
    }
}
