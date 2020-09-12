/*
 * The MIT License
 *
 *  Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package com.lhsj.police.flow.workflow;

import com.google.common.base.Throwables;
import com.lhsj.police.core.concurrent.threadpool.CompletableFutures;
import com.lhsj.police.core.lambda.ReStreams;
import com.lhsj.police.flow.work.Work;
import com.lhsj.police.flow.work.WorkContext;
import com.lhsj.police.flow.work.WorkReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

class ParallelFlowExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParallelFlowExecutor.class);

    private ExecutorService workExecutor;

    ParallelFlowExecutor(ExecutorService workExecutor) {
        this.workExecutor = workExecutor;
    }

    List<WorkReport> executeInParallel(List<Work> works, WorkContext workContext) {
        List<CompletableFuture<WorkReport>> doingFutures = ReStreams
                .ofNullable(works)
                .map(e -> createWorkFuture(e, workContext))
                .collect(Collectors.toList());

        try {
            return CompletableFutures.submitBatch(doingFutures).get();
        } catch (Throwable ex) {
            LOGGER.warn(getClass().getName(), ex);
            Throwables.throwIfUnchecked(ex);
            return emptyList();
        }
//
//        if (isEmpty(works)) {
//            return emptyList();
//        }
//
//
//        // submit work units to be executed in parallel
//        Map<Work, Future<WorkReport>> reportFutures = new HashMap<>();
//        for (Work work : works) {
//            Future<WorkReport> reportFuture = workExecutor.submit(() -> work.call(workContext));
//            reportFutures.put(work, reportFuture);
//        }
//
//        // poll for work completion
//        int finishedWorks = works.size();
//        // FIXME polling futures for completion, not sure this is the best way to run callables in parallel and wait for them to complete (use CompletionService??)
//        while (finishedWorks > 0) {
//            for (Future<WorkReport> future : reportFutures.values()) {
//                if (future != null && future.isDone()) {
//                    finishedWorks--;
//                }
//            }
//        }
//
//        // gather reports
//        List<WorkReport> workReports = new ArrayList<>();
//        for (Map.Entry<Work, Future<WorkReport>> entry : reportFutures.entrySet()) {
//            try {
//                workReports.add(entry.getValue().get());
//            } catch (InterruptedException | ExecutionException e) {
//                LOGGER.warn("Unable to get report of work unit ''{}''", entry.getKey().getName());
//            }
//        }
//
//        return workReports;
    }

    private CompletableFuture<WorkReport> createWorkFuture(Work work, WorkContext workContext) {
        Supplier<WorkReport> supplier = () -> work.call(workContext);
        return CompletableFuture.supplyAsync(supplier, workExecutor);
    }
}
