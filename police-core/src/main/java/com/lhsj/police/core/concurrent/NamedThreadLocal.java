package com.lhsj.police.core.concurrent;

import static com.lhsj.police.core.base.ReValidates.isTrue;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * {@link ThreadLocal} subclass that exposes a specified name
 * as {@link #toString()} result (allowing for introspection).
 *
 * @param <T> the value type
 * @author Juergen Hoeller
 * @see NamedInheritableThreadLocal
 * @since 2.5.2
 */
public class NamedThreadLocal<T> extends ThreadLocal<T> {

    private final String name;


    /**
     * Create a new NamedThreadLocal with the given name.
     *
     * @param name a descriptive name for this ThreadLocal
     */
    public NamedThreadLocal(String name) {
        isTrue(isNotBlank(name), "Name must not be empty");
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
