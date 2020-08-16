package com.lhsj.police.core.concurrent.threadpool;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class CompletableFutures {

    public static <T> CompletableFuture<List<T>> submitBatch(List<CompletableFuture<T>> futures) {
        final List<CompletableFuture<T>> doingFutures = Optional.ofNullable(futures).orElse(Collections.emptyList());
        CompletableFuture<Void> doneFutures = CompletableFuture.allOf(doingFutures.toArray(new CompletableFuture[0]));
        return doneFutures.thenApply(v -> doingFutures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }

}
