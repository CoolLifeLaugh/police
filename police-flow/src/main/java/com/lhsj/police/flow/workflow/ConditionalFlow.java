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

import com.lhsj.police.core.id.ReIds;
import com.lhsj.police.flow.work.NoOpWork;
import com.lhsj.police.flow.work.Work;
import com.lhsj.police.flow.work.WorkContext;
import com.lhsj.police.flow.work.WorkPredicate;
import com.lhsj.police.flow.work.WorkReport;

/**
 * A conditional flow is defined by 4 artifacts:
 *
 * <ul>
 *     <li>The work to execute first</li>
 *     <li>A predicate for the conditional logic</li>
 *     <li>The work to execute if the predicate is satisfied</li>
 *     <li>The work to execute if the predicate is not satisfied (optional)</li>
 * </ul>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 * @see ConditionalFlow.Builder
 */
public class ConditionalFlow extends AbstractWorkFlow {

    private Work toExecute, nextOnPredicateSuccess, nextOnPredicateFailure;
    private WorkPredicate predicate;

    ConditionalFlow(String name, Work toExecute, Work nextOnPredicateSuccess, Work nextOnPredicateFailure, WorkPredicate predicate) {
        super(name);
        this.toExecute = toExecute;
        this.nextOnPredicateSuccess = nextOnPredicateSuccess;
        this.nextOnPredicateFailure = nextOnPredicateFailure;
        this.predicate = predicate;
    }

    /**
     * {@inheritDoc}
     */
    public WorkReport call(WorkContext workContext) {
        WorkReport jobReport = toExecute.call(workContext);
        if (predicate.apply(jobReport)) {
            jobReport = nextOnPredicateSuccess.call(workContext);
        } else {
            if (nextOnPredicateFailure != null && !(nextOnPredicateFailure instanceof NoOpWork)) { // else is optional
                jobReport = nextOnPredicateFailure.call(workContext);
            }
        }
        return jobReport;
    }

    public static class Builder {

        private String name;
        private Work   toExecute, nextOnPredicateSuccess, nextOnPredicateFailure;
        private WorkPredicate predicate;

        private Builder() {
            this.name = ReIds.fastUUID().toString();
            this.toExecute = new NoOpWork();
            this.nextOnPredicateSuccess = new NoOpWork();
            this.nextOnPredicateFailure = new NoOpWork();
            this.predicate = WorkPredicate.ALWAYS_FALSE;
        }

        public static ConditionalFlow.Builder of() {
            return new ConditionalFlow.Builder();
        }

        public ConditionalFlow.Builder named(String name) {
            this.name = name;
            return this;
        }

        public ConditionalFlow.Builder execute(Work work) {
            this.toExecute = work;
            return this;
        }

        public ConditionalFlow.Builder when(WorkPredicate predicate) {
            this.predicate = predicate;
            return this;
        }

        public ConditionalFlow.Builder then(Work work) {
            this.nextOnPredicateSuccess = work;
            return this;
        }

        public ConditionalFlow.Builder otherwise(Work work) {
            this.nextOnPredicateFailure = work;
            return this;
        }

        public ConditionalFlow build() {
            return new ConditionalFlow(name, toExecute, nextOnPredicateSuccess, nextOnPredicateFailure, predicate);
        }
    }
}
