package com.lhsj.police.exceptions.controller;

import com.lhsj.police.exceptions.model.Person;
import com.lhsj.police.exceptions.service.ExceptionsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/exceptions")
public class ExceptionsController {

    @Resource
    private ExceptionsService exceptionsService;

    @ResponseBody
    @RequestMapping(value = "/simple", method = RequestMethod.GET)
    public boolean simple() {
        return exceptionsService.simple();
    }

    @ResponseBody
    @RequestMapping(value = "/simple/person", method = RequestMethod.GET)
    public Person person() {
        return exceptionsService.person();
    }
}
