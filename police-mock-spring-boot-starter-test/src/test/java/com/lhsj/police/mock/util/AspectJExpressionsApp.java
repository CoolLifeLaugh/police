package com.lhsj.police.mock.util;

import com.lhsj.police.core.json.ReJsons;
import com.lhsj.police.mock.controller.MockController;

import java.util.List;

import static com.lhsj.police.aspect.point.ReAopPoints.getMatchMethod;

public final class AspectJExpressionsApp {

    public static void main(String[] args) {
        String expression = "execution (* com.lhsj.police.mock.controller.MockController.*(..))";
        List<String> methods = getMatchMethod(expression, MockController.class);
        System.out.println(ReJsons.obj2String(methods));
    }
}
