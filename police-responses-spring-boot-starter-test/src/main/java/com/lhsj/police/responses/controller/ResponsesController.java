package com.lhsj.police.responses.controller;

import com.lhsj.police.exceptions.model.Person;
import com.lhsj.police.responses.annotation.Responses;
import com.lhsj.police.responses.service.ResponsesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/response")
public class ResponsesController {

    @Resource
    private ResponsesService responsesService;

    @Responses(handleIfSuccess = true)
    @ResponseBody
    @RequestMapping(value = "/simple", method = RequestMethod.GET)
    public boolean simple() {
        return responsesService.simple();
    }

    @Responses()
    @ResponseBody
    @RequestMapping(value = "/noReturn", method = RequestMethod.GET)
    public void noReturn() {
        responsesService.noReturn();
    }


    @Responses(handleIfError = true)
    @ResponseBody
    @RequestMapping(value = "/simple/exception", method = RequestMethod.GET)
    public Person person() {
        return responsesService.exception();
    }
}
