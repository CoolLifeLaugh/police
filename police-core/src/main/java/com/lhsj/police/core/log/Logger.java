package com.lhsj.police.core.log;

/**
 * 日志接口
 */
public interface Logger {

    /**
     * trace日志级别
     *
     * @param msg
     */
    void trace(String msg);

    /**
     * trace日志级别
     *
     * @param format
     * @param argArray
     */
    void trace(String format, Object... argArray);

    /**
     * debug日志级别
     *
     * @param msg
     */
    void debug(String msg);

    /**
     * debug日志级别
     *
     * @param format
     * @param argArray
     */
    void debug(String format, Object... argArray);

    /**
     * debug日志级别
     *
     * @param msg
     * @param t
     */
    void debug(String msg, Throwable t);

    /**
     * info日志级别
     *
     * @param msg
     */
    void info(String msg);

    /**
     * info日志级别
     *
     * @param format
     * @param argArray
     */
    void info(String format, Object... argArray);

    /**
     * info日志级别
     *
     * @param msg
     * @param t
     */
    void info(String msg, Throwable t);

    /**
     * warn日志级别
     *
     * @param msg
     */
    void warn(String msg);

    /**
     * warn日志级别
     *
     * @param format
     * @param argArray
     */
    void warn(String format, Object... argArray);

    /**
     * warn日志级别
     *
     * @param msg
     * @param t
     */
    void warn(String msg, Throwable t);

    /**
     * error日志级别
     *
     * @param msg
     */
    void error(String msg);

    /**
     * error日志级别
     *
     * @param format
     * @param argArray
     */
    void error(String format, Object... argArray);

    /**
     * error日志级别
     *
     * @param msg
     * @param t
     */
    void error(String msg, Throwable t);

    /**
     * 是否支持Trace日志级别
     *
     * @return
     */
    boolean isTraceEnabled();

    /**
     * 是否支持Debug日志级别
     *
     * @return
     */
    boolean isDebugEnabled();

    /**
     * 是否支持Info日志级别
     *
     * @return
     */
    boolean isInfoEnabled();

    /**
     * 是否支持warn日志级别
     *
     * @return
     */
    boolean isWarnEnabled();

    /**
     * 是否支持error日志级别
     *
     * @return
     */
    boolean isErrorEnabled();

}