package com.lhsj.police.core.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Predicate;

import static com.lhsj.police.core.base.ReExceptions.unchecked;
import static com.lhsj.police.core.base.Validate.isTrue;

/**
 * 简单重试工具
 * <p>
 * 如果使用功能丰富的重试工具，请使用spring-retry框架
 */
@SuppressWarnings("unused")
public final class Retrys {

    private static final Logger logger = LoggerFactory.getLogger(Retrys.class);

    private Retrys() {
    }

    public static <T> T execute(@Nonnull final Callable<T> func) {
        return execute(func, DEFAULT_CAN_RETRY, DEFAULT_MAX_TRIES);
    }

    public static <T> T execute(@Nonnull final Callable<T> func,
                                @Nonnegative final int maxTries) {
        return execute(func, DEFAULT_CAN_RETRY, maxTries);
    }

    public static <T> T execute(@Nonnull final Callable<T> func,
                                @Nonnull Predicate<Throwable> canRetry) {
        return execute(func, canRetry, DEFAULT_MAX_TRIES);
    }

    /**
     * 重试执行
     *
     * @param func     执行的具体功能
     * @param canRetry 是否能重试
     * @param maxTries 最大重试次数
     */
    public static <T> T execute(@Nonnull final Callable<T> func,
                                @Nonnull Predicate<Throwable> canRetry,
                                @Nonnegative final int maxTries) {
        isTrue(maxTries > 0, "maxTries > 0");

        int nTry = 0;
        while (true) {
            try {
                nTry++;
                return func.call();
            } catch (Throwable e) {
                if (nTry < maxTries && canRetry.test(e)) {
                    awaitNextRetry(nTry);
                } else {
                    unchecked(e);
                }
            }
        }
    }

    public static void execute(@Nonnull final Runnable func) {
        execute(func, DEFAULT_CAN_RETRY, DEFAULT_MAX_TRIES);
    }

    public static void execute(@Nonnull final Runnable func,
                               @Nonnegative final int maxTries) {
        execute(func, DEFAULT_CAN_RETRY, maxTries);
    }

    public static void execute(@Nonnull final Runnable func,
                               @Nonnull Predicate<Throwable> canRetry) {
        execute(func, canRetry, DEFAULT_MAX_TRIES);
    }

    /**
     * 重试执行
     *
     * @param func     执行的具体功能
     * @param canRetry 是否能重试
     * @param maxTries 最大重试次数
     */
    public static void execute(@Nonnull final Runnable func,
                               @Nonnull Predicate<Throwable> canRetry,
                               @Nonnegative final int maxTries) {
        isTrue(maxTries > 0, "maxTries > 0");

        int nTry = 0;
        while (true) {
            try {
                nTry++;
                func.run();
                return;
            } catch (Throwable e) {
                if (nTry < maxTries && canRetry.test(e)) {
                    awaitNextRetry(nTry);
                } else {
                    unchecked(e);
                }
            }
        }
    }

    private static final Predicate<Throwable> DEFAULT_CAN_RETRY = e -> true;
    private static final int                  DEFAULT_MAX_TRIES = 3;

    private static final long BASE_SLEEP_MILLIS = 1000;
    private static final long MAX_SLEEP_MILLIS  = 60000;

    private static void awaitNextRetry(final int nTry) {
        final double fuzzyMultiplier = Math.min(Math.max(1 + 0.2 * ThreadLocalRandom.current().nextGaussian(), 0), 2);
        final long sleepMillis = (long) (Math.min(MAX_SLEEP_MILLIS, BASE_SLEEP_MILLIS * Math.pow(2, nTry)) * fuzzyMultiplier);

        if (logger.isDebugEnabled()) {
            logger.debug("Failed on try {}, retrying in {}ms.", nTry, sleepMillis);
        }

        LockSupport.parkNanos(sleepMillis * 1_000_000L);
    }
}
