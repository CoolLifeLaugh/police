package com.lhsj.police.context.controller;

import com.lhsj.police.context.holder.ApplicationContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/context")
public class ContextController {

    @ResponseBody
    @RequestMapping(value = "/simple", method = RequestMethod.GET)
    public String simple() {
        return ApplicationContextHolder.getContext().getId();
    }

}
