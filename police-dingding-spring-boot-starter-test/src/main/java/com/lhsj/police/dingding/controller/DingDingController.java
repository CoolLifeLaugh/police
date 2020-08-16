package com.lhsj.police.dingding.controller;

import com.lhsj.police.dingding.configuration.DingDingProperties;
import com.lhsj.police.dingding.service.DingDingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/dingding")
public class DingDingController {

    @Resource
    private DingDingService    dingDingService;
    @Resource
    private DingDingProperties dingDingProperties;

    @ResponseBody
    @RequestMapping(value = "/send/text", method = RequestMethod.GET)
    public boolean sendText() {
        try {
            return dingDingService.sendText();
        } catch (Throwable e) {
            return true;
        }
    }

}
