package com.lhsj.police.flow.example;

import com.lhsj.police.core.json.ReJsons;
import com.lhsj.police.flow.engine.WorkFlowEngine;
import com.lhsj.police.flow.engine.WorkFlowEngineBuilder;
import com.lhsj.police.flow.work.DefaultWorkReport;
import com.lhsj.police.flow.work.Work;
import com.lhsj.police.flow.work.WorkContext;
import com.lhsj.police.flow.work.WorkReport;
import com.lhsj.police.flow.work.WorkPredicate;
import com.lhsj.police.flow.work.WorkStatus;
import com.lhsj.police.flow.workflow.ConditionalFlow;
import com.lhsj.police.flow.workflow.ParallelFlow;
import com.lhsj.police.flow.workflow.RepeatFlow;
import com.lhsj.police.flow.workflow.SequentialFlow;
import com.lhsj.police.flow.workflow.WorkFlow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleApp {


    public static void main(String[] args) {
        PrintMessageWork work1 = new PrintMessageWork("work1 foo");
        PrintMessageWork work2 = new PrintMessageWork("work2 hello");
        PrintMessageWork work3 = new PrintMessageWork("work3 world");
        PrintMessageWork work4 = new PrintMessageWork("work4 ok");
        PrintMessageWork work5 = new PrintMessageWork("work5 nok");

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        WorkFlow workflow = SequentialFlow.Builder.aNewSequentialFlow() // flow 4
                .execute(
                        RepeatFlow.Builder.aNewRepeatFlow() // flow 1
                                .named("print foo 3 times")
                                .repeat(work1)
                                .times(3)
                                .build()
                )
                .then(
                        ConditionalFlow.Builder.of() // flow 3
                                .execute(
                                        ParallelFlow.Builder.of(executorService) // flow 2
                                                .named("print 'hello' and 'world' in parallel")
                                                .execute(work2, work3)
                                                .build()
                                )
                                .when(WorkPredicate.FAILED)
                                .then(work4)
                                .otherwise(work5)
                                .build()
                )
                .build();

        WorkFlowEngine workFlowEngine = WorkFlowEngineBuilder.of().build();
        WorkContext workContext = new WorkContext();
        WorkReport workReport = workFlowEngine.run(workflow, workContext);
        System.out.println(ReJsons.obj2String(workReport));
        executorService.shutdown();
    }

    static class PrintMessageWork implements Work {

        private String message;

        public PrintMessageWork(String message) {
            this.message = message;
        }

        public String getName() {
            return "print message work";
        }

        public WorkReport call(WorkContext workContext) {
            System.out.println(message);
            return new DefaultWorkReport(WorkStatus.COMPLETED, workContext);
        }
    }

}
