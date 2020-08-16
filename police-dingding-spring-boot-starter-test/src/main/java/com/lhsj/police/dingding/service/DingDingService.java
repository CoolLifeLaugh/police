package com.lhsj.police.dingding.service;

import com.lhsj.police.dingding.annotation.DingDing;
import org.springframework.stereotype.Service;

@Service
public class DingDingService {

    private static final String webhook = "https://oapi.dingtalk.com/robot/send?access_token=1d2f25663406d1a65729ab2ae979469b96d4b15a2454034d42c6223d03dd0bc9";
    private static final String keyword = "test";
    private static final String sign    = "SEC170bb4ac78971fba6d23f00e9f336f0f8ace8a1e88145782eff1e66f9d070d24";

    @DingDing(webhook = webhook, keyword = keyword, sign = sign)
    public boolean sendText() {
        if (true) {
            throw new RuntimeException("");
        }
        return true;
    }
}
