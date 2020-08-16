package com.lhsj.police.core.rule.exception;

import com.lhsj.police.core.base.ReValidates;

import java.io.Serializable;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ExceptionRuleAttribute implements Serializable {

    private final String exceptionName;

    public ExceptionRuleAttribute(Class<?> clazz) {
        ReValidates.isTrue(nonNull(clazz), "'clazz' cannot be null");
        if (!Throwable.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Cannot construct rule from [" + clazz.getName() + "]: it's not a Throwable");
        }
        this.exceptionName = clazz.getName();
    }


    public ExceptionRuleAttribute(String exceptionName) {
        ReValidates.isTrue(isNotBlank(exceptionName), "'exceptionName' cannot be null or empty");
        this.exceptionName = exceptionName;
    }


    public String getExceptionName() {
        return exceptionName;
    }

    /**
     * Return the depth of the superclass matching.
     * <p>{@code 0} means {@code ex} matches exactly. Returns
     * {@code -1} if there is no match. Otherwise, returns depth with the
     * lowest depth winning.
     */
    public int getDepth(Throwable ex) {
        return getDepth(ex.getClass(), 0);
    }

    private int getDepth(Class<?> exceptionClass, int depth) {
        if (exceptionClass.getName().contains(this.exceptionName)) {
            // Found it!
            return depth;
        }
        // If we've gone as far as we can go and haven't found it...
        if (exceptionClass == Throwable.class) {
            return -1;
        }
        return getDepth(exceptionClass.getSuperclass(), depth + 1);
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExceptionRuleAttribute)) {
            return false;
        }
        ExceptionRuleAttribute rhs = (ExceptionRuleAttribute) other;
        return this.exceptionName.equals(rhs.exceptionName);
    }

    @Override
    public int hashCode() {
        return this.exceptionName.hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName() + " with pattern [" + this.exceptionName + "]";
    }

}
