package com.lhsj.police.core.naming;

public abstract class AbstractName implements Name {

    String name;

    public AbstractName(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
