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
package com.lhsj.police.flow.work;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A predicate interface on work report.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@FunctionalInterface
public interface WorkPredicate {

    /**
     * Apply the predicate on the given work report.
     *
     * @param workReport on which the predicate should be applied
     * @return true if the predicate applies on the given report, false otherwise
     */
    boolean apply(WorkReport workReport);

    WorkPredicate ALWAYS_TRUE  = workReport -> true;
    WorkPredicate ALWAYS_FALSE = workReport -> false;
    WorkPredicate COMPLETED    = workReport -> workReport.getStatus().equals(WorkStatus.COMPLETED);
    WorkPredicate FAILED       = workReport -> workReport.getStatus().equals(WorkStatus.FAILED);

    /**
     * A predicate that returns true after a given number of times.
     *
     * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
     */
    class TimesPredicate implements WorkPredicate {

        private int times;

        private AtomicInteger counter = new AtomicInteger();

        public TimesPredicate(int times) {
            this.times = times;
        }

        @Override
        public boolean apply(WorkReport workReport) {
            return counter.incrementAndGet() != times;
        }

        public static TimesPredicate times(int times) {
            return new TimesPredicate(times);
        }
    }


}
