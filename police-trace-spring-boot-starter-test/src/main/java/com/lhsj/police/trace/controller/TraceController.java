package com.lhsj.police.trace.controller;

import com.lhsj.police.trace.service.TraceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/trace")
public class TraceController {

    @Resource
    private TraceService traceService;

    @ResponseBody
    @RequestMapping(value = "/simple", method = RequestMethod.GET)
    public boolean simple() {
        try {
            return traceService.simple("traceParam");
        } catch (Throwable e) {
            return true;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    public boolean exception() {
        try {
            return traceService.exception("traceParam");
        } catch (Throwable e) {
            return true;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/exception/code", method = RequestMethod.GET)
    public boolean codeException() {
        try {
            return traceService.codeException("traceParam");
        } catch (Throwable e) {
            return true;
        }
    }
}
