package com.lhsj.police.mock.util;

import com.lhsj.police.core.json.ReJsons;
import com.lhsj.police.mock.controller.MockController;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public final class AspectJExpressionsApp {

    public static void main(String[] args) throws MalformedURLException {
        String expression = "execution (* com.lhsj.police.mock.controller.MockController.*(..))";
        List<String> methods = AspectJExpressions.getMatchMethod(expression, MockController.class);
        System.out.println(ReJsons.obj2String(methods));

        URL url = new URL("http://localhost:8080/mock/simple?k1=1&k2=1");
        System.out.println(url.getQuery());
    }
}
