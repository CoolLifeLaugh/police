package com.lhsj.police.core.concurrent;

public class ReLocks {

    private static final VersionException EXCEPTION = new VersionException();

    /**
     * 使用乐观锁机制, 执行更新任务
     * <p>
     * executor.handle(source), 此方法需要实现版本控制
     * <p>
     * 例如: DB中, sql写成 update tablename set column1 = 1, version = version + 1 where id = 1 and version = {version}
     *
     * @param executor 执行器
     * @param times    循环次数: <= -1, 表示不限制; > 0, 表示指定循环执行次数; = 0, 表示禁止使用
     * @param <T>      task
     */
    public static <T> void execute(Executor<T> executor, int times) {
        int nowTimes = times;
        while (true) {
            try {
                if (nowTimes == 0) {
                    throw new TimesOverException("times over");
                }

                T source = executor.source();

                executor.preHandle(source);

                if (!executor.handle(source)) {
                    nowTimes--;
                    throw EXCEPTION;
                }

                executor.postHandle(source);

                break;
            } catch (VersionException ve) {
                continue;
            }
        }
    }

    public interface Executor<T> {
        /**
         * 获取数据domain
         *
         * @return T
         */
        T source();

        /**
         * 前置处理
         *
         * @param source domain
         */
        void preHandle(T source);

        /**
         * 处理
         *
         * @param source
         * @return
         */
        boolean handle(T source);

        /**
         * 后置处理
         *
         * @param source domain
         */
        void postHandle(T source);
    }

    private static class VersionException extends RuntimeException {
        @Override
        public synchronized Throwable fillInStackTrace() {
            return null;
        }
    }

    private static class TimesOverException extends RuntimeException {
        private TimesOverException() {
            super();
        }

        private TimesOverException(String message) {
            super(message);
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            return null;
        }
    }
}
