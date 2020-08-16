package com.lhsj.police.responses.service;

import com.lhsj.police.exceptions.model.Person;
import org.springframework.stereotype.Service;

@Service
public class ResponsesService {

    public boolean simple() {
        return true;
    }

    public void noReturn() {
    }

    public Person exception() {
        if (true) {
            throw new RuntimeException("mock");
        }
        return new Person();
    }


}
