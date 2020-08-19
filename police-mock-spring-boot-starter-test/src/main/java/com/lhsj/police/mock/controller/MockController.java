package com.lhsj.police.mock.controller;

import com.lhsj.police.mock.service.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mock")
public class MockController {

    @Resource
    private Service service;

    @ResponseBody
    @RequestMapping(value = "/simple", method = RequestMethod.GET)
    public boolean simple() {
        return service.simple();
    }

    @ResponseBody
    @RequestMapping(value = "/remote", method = RequestMethod.GET)
    public boolean remote() {
        return service.remote();
    }

}
