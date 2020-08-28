package com.lhsj.police.limiter.controller;

import com.lhsj.police.core.log.ReLogs;
import com.lhsj.police.limiter.service.LimiterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/limit")
public class LimiterController {

    @Resource
    private LimiterService service;

    IntConsumer simpleConsumer = e -> {
        try {
            service.simple();
            System.out.println("service.simple() success, index = " + e);
        } catch (Throwable ex) {
            System.out.println("service.simple() fail, index = " + e + ", msg = " + ex.getMessage());
            ReLogs.warn(getClass().getName(), ex);
        }
    };

    @ResponseBody
    @RequestMapping(value = "/simple", method = RequestMethod.GET)
    public void simple() {
        IntStream.range(0, 100).parallel().forEach(simpleConsumer);
    }

    IntConsumer thresholdConsumer = e -> {
        try {
            service.threshold();
            System.out.println("service.threshold() success, index = " + e);
        } catch (Throwable ex) {
            System.out.println("service.threshold() fail, index = " + e + ", msg = " + ex.getMessage());
            ReLogs.warn(getClass().getName(), ex);

        }
    };

    @ResponseBody
    @RequestMapping(value = "/threshold", method = RequestMethod.GET)
    public void threshold() {
        IntStream.range(0, 100).parallel().forEach(thresholdConsumer);
    }

    IntConsumer timeoutConsumer = e -> {
        try {
            service.timeout();
            System.out.println("service.timeout() success, index = " + e);
        } catch (Throwable ex) {
            System.out.println("service.timeout() fail, index = " + e + ", msg = " + ex.getMessage());
            ReLogs.warn(getClass().getName(), ex);
        }
    };

    @ResponseBody
    @RequestMapping(value = "/timeout", method = RequestMethod.GET)
    public void timeout() {
        IntStream.range(0, 100).parallel().forEach(timeoutConsumer);
    }

}
