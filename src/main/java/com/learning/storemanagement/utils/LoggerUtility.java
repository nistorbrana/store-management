package com.learning.storemanagement.utils;

import org.slf4j.Logger;

import java.util.Map;

public class LoggerUtility {

    public static void logEventInfo(Logger logger, String event, String status, String message) {
        logger.info("[event={}, status={}, message=\"{}\"]", event, status, message);
    }

    public static void logEventWarn(Logger logger, String event, String status, String message) {
        logger.warn("[event={}, status={}, message=\"{}\"]", event, status, message);
    }

    public static void logEventWarn(Logger logger, String event, String source, Map<String,String> errors) {
        logger.warn("[event={}, source={}, errors={}]", event, source, errors);
    }

    public static void logEnumParseExWarn(Logger logger, String event, String field, String message) {
        logger.warn("[event={}, field={}, message={}]",event, field, message);
    }
}
