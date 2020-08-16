package com.lhsj.police.core.rule.exception;

import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import static com.lhsj.police.core.lambda.ReStreams.ofNullable;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

public class RuleBasedExceptionAttribute extends DefaultExceptionAttribute {

    private List<ExceptionRuleAttribute> ruleAttributes;

    private static final Log logger = LogFactory.getLog(RuleBasedExceptionAttribute.class);

    public RuleBasedExceptionAttribute() {
    }

    public RuleBasedExceptionAttribute(List<ExceptionRuleAttribute> ruleAttributes) {
        this.ruleAttributes = ruleAttributes;
    }

    public static RuleBasedExceptionAttribute of() {
        return new RuleBasedExceptionAttribute();
    }

    public RuleBasedExceptionAttribute rule(ExceptionRuleAttribute rule) {
        if (isNull(ruleAttributes)) {
            ruleAttributes = Lists.newArrayList(rule);
        } else {
            ruleAttributes.add(rule);
        }
        return this;
    }

    public RuleBasedExceptionAttribute rules(List<ExceptionRuleAttribute> exceptionRuleAttributes) {
        ofNullable(exceptionRuleAttributes).forEach(this::rule);
        return this;
    }

    @Override
    public boolean match(Throwable ex) {
        ExceptionRuleAttribute winner = null;
        int deepest = Integer.MAX_VALUE;

        if (isNotEmpty(ruleAttributes)) {
            for (ExceptionRuleAttribute rule : this.ruleAttributes) {
                int depth = rule.getDepth(ex);
                if (depth >= 0 && depth < deepest) {
                    deepest = depth;
                    winner = rule;
                }
            }
        }

        if (logger.isTraceEnabled()) {
            logger.trace("Winning rule is: " + winner);
        }

        return nonNull(winner) || super.match(ex);
    }
}
