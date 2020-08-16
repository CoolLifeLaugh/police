package com.lhsj.police.core.rule.exception;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class RuleExceptions {

    public static boolean match(Class<? extends Throwable>[] consults, Throwable ex) {
        return match(ofNullable(consults).map(Arrays::asList).orElse(emptyList()), ex);
    }

    public static boolean match(List<Class<? extends Throwable>> consults, Throwable ex) {
        List<ExceptionRuleAttribute> rules = ofNullable(consults)
                .orElse(emptyList())
                .stream()
                .map(ExceptionRuleAttribute::new)
                .collect(toList());

        return RuleBasedExceptionAttribute.of().rules(rules).match(ex);
    }
}
