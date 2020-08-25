package com.lhsj.police.core.retry;

public class RetrysApp {

    public static void main(String[] args) {
        testRunnable();
        testCallable();
    }

    private static void testRunnable() {
        Retrys.execute(() -> {
            if (true) {
                throw new RuntimeException("mock runnable exception");
            }
        });
    }

    private static void testCallable() {
        int result = Retrys.execute(() -> {
            if (true) {
                throw new RuntimeException("mock callable exception");
            }
            return 1;
        });
        System.out.println(result);
    }

}
