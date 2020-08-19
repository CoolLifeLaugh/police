package com.lhsj.police.core.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.lhsj.police.core.regex.RePatterns.PATTERN_LEAST_NUMBER;
import static com.lhsj.police.core.regex.RePatterns.PATTERN_MIN_MAX_NUMBER;
import static com.lhsj.police.core.regex.RePatterns.PATTERN_NUMBER;
import static com.lhsj.police.core.regex.RePatterns.PATTERN_N_NUMBER;
import static com.lhsj.police.core.text.ReStrings.format;
import static java.util.Optional.ofNullable;

public final class ReRegexs {

    /**
     * 校验文本，是否匹配指定的正则表达式
     *
     * @param pattern 编译过的表达式
     * @param text    待校验的文本
     */
    public static boolean match(Pattern pattern, String text) {
        return ofNullable(pattern)
                .map(e -> pattern.matcher(text))
                .map(Matcher::matches)
                .orElse(false);
    }

    /**
     * 校验文本，是否匹配指定的正则表达式
     *
     * @param pattern 表达式
     * @param text    待校验的文本
     */
    public static boolean match(String pattern, String text) {
        return Pattern.matches(pattern, text);
    }

    /**
     * 动态校验文本，是否匹配指定的正则表达式
     *
     * @param pattern 表达式
     * @param text    待校验的文本
     * @param args    动态参数
     */
    public static boolean match(String pattern, String text, Object... args) {
        return Pattern.matches(format(pattern, args), text);
    }

    // ------------ 校验数字 ------------

    /**
     * 校验数字
     *
     * @param text 待校验的文本
     */
    public static boolean isNumber(String text) {
        return match(PATTERN_NUMBER, text);
    }

    /**
     * 校验数字的个数
     *
     * @param text 待校验的文本
     * @param num  数字的个数
     */
    public static boolean isNumber(String text, int num) {
        return match(PATTERN_N_NUMBER, text, num);
    }

    public static boolean isLeastNumber(String text, int num) {
        return match(PATTERN_LEAST_NUMBER, text, num);
    }

    public static boolean isMinMaxNumber(String text, int minNum, int maxNum) {
        return match(PATTERN_MIN_MAX_NUMBER, text, minNum, maxNum);
    }

    // ------------ 校验字符 ------------

    // ------------ 校验特定场景 ------------
}
