package com.lhsj.police.dislock.redis.controller;

import com.lhsj.police.dislock.redis.service.DislockRedisService;
import com.lhsj.police.trace.Traces;
import com.lhsj.police.trace.model.TraceLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/dislock/redis")
public class DislockRedisController {

    @Resource
    private DislockRedisService dislockRedisService;

    IntConsumer consumer = e -> {
        TraceLog log = Traces.start().type("dislock_simple");
        try {
            log.key("key={}", e);
            dislockRedisService.simple("simple");
            log.success();
        } catch (Throwable ex) {
            log.fail();
        } finally {
            log.log();
        }
    };

    @ResponseBody
    @RequestMapping(value = "/simple", method = RequestMethod.GET)
    public boolean simple() {
        IntStream.range(0, 100000).parallel().forEach(consumer);
        return true;
    }


}
