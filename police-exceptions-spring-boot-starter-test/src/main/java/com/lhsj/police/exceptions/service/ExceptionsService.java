package com.lhsj.police.exceptions.service;

import com.lhsj.police.exceptions.annotation.ExceptionResolver;
import com.lhsj.police.exceptions.model.Person;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class ExceptionsService implements InitializingBean {


    @ExceptionResolver()
    public boolean simple() {
        if (true) {
            throw new RuntimeException("mock");
        }
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @ExceptionResolver()
    public Person person() {
        if (true) {
            throw new RuntimeException("mock");
        }
        return new Person();
    }
}
