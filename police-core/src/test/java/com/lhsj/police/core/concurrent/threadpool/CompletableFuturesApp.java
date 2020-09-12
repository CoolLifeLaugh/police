package com.lhsj.police.core.concurrent.threadpool;

import com.lhsj.police.core.json.ReJsons;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFuturesApp {

    private static ThreadPoolExecutor fixExecutor = ThreadPoolBuilder
            .fixedPool()
            .setThreadNamePrefix("fixExecutor")
            .setPoolSize(3).build();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        testDefaultExecutor();

        testExternalExecutor();

        fixExecutor.shutdown();
    }

    private static void testExternalExecutor() throws InterruptedException, ExecutionException {
        List<CompletableFuture<Integer>> doingFutures = IntStream
                .range(1, 7)
                .mapToObj(CompletableFuturesApp::createExternalFuture)
                .collect(Collectors.toList());


        List<Integer> result = CompletableFutures.submitBatch(doingFutures).get();
        System.out.println(ReJsons.obj2String(result));
    }

    private static CompletableFuture<Integer> createExternalFuture(int e) {
        Supplier<Integer> supplier = () -> {
            System.out.println(Thread.currentThread().getName());

            if (e > 2) {
                return null;
            }
            return e * e;
        };

        return CompletableFuture.supplyAsync(supplier, fixExecutor);
    }

    private static void testDefaultExecutor() throws InterruptedException, ExecutionException {
        List<CompletableFuture<Integer>> doingFutures = IntStream
                .range(1, 7)
                .mapToObj(CompletableFuturesApp::createFuture)
                .collect(Collectors.toList());


        List<Integer> result = CompletableFutures.submitBatch(doingFutures).get();
        System.out.println(ReJsons.obj2String(result));
    }

    private static CompletableFuture<Integer> createFuture(int e) {
        Supplier<Integer> supplier = () -> e * e;

        return CompletableFuture.supplyAsync(supplier);
    }

}
