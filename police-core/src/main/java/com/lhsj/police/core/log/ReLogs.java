package com.lhsj.police.core.log;

import com.lhsj.police.core.text.ReStrings;

/**
 * default log util
 */
public final class ReLogs {

    private static Logger logger = new DefaultLogger();

    // ------------------- 基本log方法 -----------------------------

    public static boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public static boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public static boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    public static boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    public static void trace(String msg) {
        logger.trace(msg);
    }

    public static void debug(String msg) {
        logger.debug(msg);
    }

    public static void debug(String format, Object... argArray) {
        logger.debug(format, argArray);
    }

    public static void debug(String msg, Throwable t) {
        logger.debug(msg, t);
    }

    public static void debug(Throwable t, String format, Object... argArray) {
        logger.debug(ReStrings.format(format, argArray), t);
    }

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void info(String format, Object... argArray) {
        logger.info(format, argArray);
    }

    public static void info(String msg, Throwable t) {
        logger.info(msg, t);
    }

    public static void info(Throwable t, String format, Object... argArray) {
        logger.info(ReStrings.format(format, argArray), t);
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }

    public static void warn(String format, Object... argArray) {
        logger.warn(format, argArray);
    }

    public static void warn(String msg, Throwable t) {
        logger.warn(msg, t);
    }

    public static void warn(Throwable t, String format, Object... argArray) {
        logger.warn(ReStrings.format(format, argArray), t);
    }

    public static void error(String msg) {
        logger.error(msg);
    }

    public static void error(String format, Object... argArray) {
        logger.error(format, argArray);
    }

    public static void error(String msg, Throwable t) {
        logger.error(msg, t);
    }

    public static void error(Throwable t, String format, Object... argArray) {
        logger.error(ReStrings.format(format, argArray), t);
    }

    // ------------------- 支持logger -----------------------------

    public static void trace(org.slf4j.Logger logger, String msg) {
        logger.trace(msg);
    }

    public static void debug(org.slf4j.Logger logger, String msg) {
        logger.debug(msg);
    }

    public static void debug(org.slf4j.Logger logger, String format, Object... argArray) {
        logger.debug(format, argArray);
    }

    public static void debug(org.slf4j.Logger logger, String msg, Throwable t) {
        logger.debug(msg, t);
    }

    public static void debug(org.slf4j.Logger logger, Throwable t, String format, Object... argArray) {
        logger.debug(ReStrings.format(format, argArray), t);
    }

    public static void info(org.slf4j.Logger logger, String msg) {
        logger.info(msg);
    }

    public static void info(org.slf4j.Logger logger, String format, Object... argArray) {
        logger.info(format, argArray);
    }

    public static void info(org.slf4j.Logger logger, String msg, Throwable t) {
        logger.info(msg, t);
    }

    /**
     * format的实现类是MessageFormatter，它的格式【"xxxxx{}xxxxx{}xxxx"】，和logger保持一致
     */
    public static void info(org.slf4j.Logger logger, Throwable t, String format, Object... argArray) {
        logger.info(ReStrings.format(format, argArray), t);
    }

    public static void warn(org.slf4j.Logger logger, String msg) {
        logger.warn(msg);
    }

    public static void warn(org.slf4j.Logger logger, String format, Object... argArray) {
        logger.warn(format, argArray);
    }

    public static void warn(org.slf4j.Logger logger, String msg, Throwable t) {
        logger.warn(msg, t);
    }

    public static void warn(org.slf4j.Logger logger, Throwable t, String format, Object... argArray) {
        logger.warn(ReStrings.format(format, argArray), t);
    }

    public static void error(org.slf4j.Logger logger, String msg) {
        logger.error(msg);
    }

    public static void error(org.slf4j.Logger logger, String format, Object... argArray) {
        logger.error(format, argArray);
    }

    public static void error(org.slf4j.Logger logger, String msg, Throwable t) {
        logger.error(msg, t);
    }

    public static void error(org.slf4j.Logger logger, Throwable t, String format, Object... argArray) {
        logger.error(ReStrings.format(format, argArray), t);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        ReLogs.logger = logger;
    }

    //-------------------private constructor------------------

    private ReLogs() {
    }

}
