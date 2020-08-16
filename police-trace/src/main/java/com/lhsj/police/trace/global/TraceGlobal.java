package com.lhsj.police.trace.global;

import com.lhsj.police.core.net.ReNets;
import com.lhsj.police.trace.logger.TraceLoggerFactory;
import org.slf4j.Logger;

import java.util.function.Predicate;

/**
 * 承载一些全局通用的属性，减少TraceLog的调用方法
 */
public final class TraceGlobal {

    public static final String loggerName = "tracelogger";
    public static       Logger logger;

    static {
        logger = TraceLoggerFactory.getLogger(loggerName);
    }

    public static String            app;
    public static String            ip = ReNets.getLocalHost();
    /**
     * traceId是否命中采样，场景是，有的trace应用，对日志有采样率的限制
     */
    public static Predicate<String> bingoPredicate;
}
