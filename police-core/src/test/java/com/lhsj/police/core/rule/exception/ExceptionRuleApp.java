package com.lhsj.police.core.rule.exception;

import com.google.common.collect.Lists;
import com.lhsj.police.core.exception.ArgumentException;
import com.lhsj.police.core.exception.BizException;

import java.util.List;

public class ExceptionRuleApp {

    public static void main(String[] args) {
        simples();
    }

    private static void simple() {
        try {
            Integer i = 1;
            Integer j = null;

            int result = i / j;
        } catch (Throwable ex) {
            List<ExceptionRuleAttribute> rules = Lists.newArrayList(
                    new ExceptionRuleAttribute(BizException.class),
                    new ExceptionRuleAttribute(NullPointerException.class),
                    new ExceptionRuleAttribute(ArgumentException.class)
            );
            RuleBasedExceptionAttribute exceptionAttribute = RuleBasedExceptionAttribute.of().rules(rules);

            System.out.println(ex.getClass().getName());
            System.out.println(exceptionAttribute.match(ex));
        }
    }

    private static void simples() {
        try {
            Integer i = 1;
            Integer j = null;

            int result = i / j;
        } catch (Throwable ex) {
            List<Class<? extends Throwable>> rules = Lists.newArrayList(
                    BizException.class,
                    NullPointerException.class,
                    ArgumentException.class
            );

            System.out.println(ex.getClass().getName());
            System.out.println(RuleExceptions.match(rules, ex));
        }
    }
}
