package com.lhsj.police.trace.logger;

import com.alipay.sofa.common.log.LoggerSpaceManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import static java.util.Optional.ofNullable;

@SuppressWarnings("unused")
public class TraceLoggerFactory {

    private static final String OCR_LOGGER_SPACE = "trace";

    public static Logger getLogger(String name) {
        return ofNullable(name)
                .filter(StringUtils::isNotBlank)
                .map(TraceLoggerFactory::doGetLogger)
                .orElse(null);
    }

    public static Logger getLogger(Class<?> clazz) {
        return ofNullable(clazz)
                .map(Class::getCanonicalName)
                .map(TraceLoggerFactory::getLogger)
                .orElse(null);
    }

    private static Logger doGetLogger(String name) {
        return LoggerSpaceManager.getLoggerBySpace(name, OCR_LOGGER_SPACE);
    }
}
