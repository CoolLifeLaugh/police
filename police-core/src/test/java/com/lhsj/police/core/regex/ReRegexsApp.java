package com.lhsj.police.core.regex;

public class ReRegexsApp {

    public static void main(String[] args) {
        System.out.println(ReRegexs.isNumber("1"));
        System.out.println(ReRegexs.isNumber("1", 1));
        System.out.println(ReRegexs.isNumber("21", 1));

        System.out.println("isLeastNumber");
        System.out.println(ReRegexs.isLeastNumber("21", 2));
        System.out.println(ReRegexs.isLeastNumber("21", 3));

        System.out.println("isMinMaxNumber");
        System.out.println(ReRegexs.isMinMaxNumber("123", 1, 3));
        System.out.println(ReRegexs.isMinMaxNumber("123", 1, 2));
    }

}
