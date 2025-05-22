package com.learning.storemanagement.utils;

import org.slf4j.Logger;

public class LoggerUtility {

    public static void logEventInfo(Logger logger, String event, String status, String message) {
        logger.info("[event={}, status={}, message=\"{}\"]", event, status, message);
    }

    public static void logEventWarn(Logger logger, String event, String status, String message) {
        logger.warn("[event={}, status={}, message=\"{}\"]", event, status, message);
    }
}
