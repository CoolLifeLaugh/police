package com.lhsj.police.core.regex;

import java.util.regex.Pattern;

@SuppressWarnings("unused")
public final class RePatterns {

    private RePatterns() {
    }

    // ------------ 校验数字 ------------

    /**
     * 正则表达式：数字
     */
    public static final Pattern PATTERN_NUMBER              = Pattern.compile("^[0-9]*$");
    /**
     * 正则表达式：n位的数字
     */
    public static final String  PATTERN_N_NUMBER            = "^\\d{{}}$";
    /**
     * 正则表达式：至少n位的数字
     */
    public static final String  PATTERN_LEAST_NUMBER        = "^\\d{{},}$";
    /**
     * 常用正则表达式：匹配整数
     */
    public final static Pattern PATTERN_INTEGER             = Pattern.compile("^-?\\d+$");
    /**
     * 正则表达式：大于n位小于m位的数字
     */
    public static final String  PATTERN_MIN_MAX_NUMBER      = "^\\d{{},{}}$";
    /**
     * 常用正则表达式：匹配正整数
     */
    public final static Pattern PATTERN_POSITIVE_INTEGER    = Pattern.compile("^[0-9]*[1-9][0-9]*$");
    /**
     * 常用正则表达式：匹配非负整数（正整数 + 0）
     */
    public final static Pattern PATTERN_NONNEGATIVE_INTEGER = Pattern.compile("^\\d+$");
    /**
     * 常用正则表达式：匹配负整数
     */
    public final static Pattern PATTERN_NEGATIVE_INTEGER    = Pattern.compile("^-[0-9]*[1-9][0-9]*$");
    /**
     * 常用正则表达式：匹配非正整数（负整数 + 0）
     */
    public final static Pattern PATTERN_NONPOSITIVE_INTEGER = Pattern.compile("^((-\\d+)?(0+))$");
    /**
     * 常用正则表达式：匹配浮点数
     */
    public final static Pattern PATTERN_FLOAT               = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
    /**
     * 常用正则表达式：匹配正浮点数
     */
    public final static Pattern PATTERN_POSITIVE_FLOAT      = Pattern.compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*) ?([0-9]*[1-9][0-9]*\\.[0-9]+)?([0-9]*[1-9][0-9]*))$");
    /**
     * 常用正则表达式：匹配非负浮点数（正浮点数 + 0）
     */
    public final static Pattern PATTERN_NONNEGATIVE_FLOAT   = Pattern.compile("^\\d+(\\.\\d+)?$");
    /**
     * 常用正则表达式：匹配负浮点数
     */
    public final static Pattern PATTERN_NEGATIVE_FLOAT      = Pattern.compile("^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)?([0-9]*[1-9][0-9]*\\.[0-9]+)?([0-9]*[1-9][0-9]*)))$");
    /**
     * 常用正则表达式：匹配非正浮点数（负浮点数 + 0）
     */
    public final static Pattern PATTERN_NONPOSITIVE_FLOAT   = Pattern.compile("^((-\\d+(\\.\\d+)?) ?(0+(\\.0+)?))$");

    // ------------ 校验字符 ------------

    /**
     * 常用正则表达式：匹配汉字
     */
    public final static Pattern PATTERN_CHINESE                          = Pattern.compile("^[\\u4e00-\\u9fa5]*$");
    /**
     * 常用正则表达式：匹配英文
     */
    public final static Pattern PATTERN_ENGLISH                          = Pattern.compile("^[A-Za-z]+$");
    /**
     * 常用正则表达式：匹配大写英文
     */
    public final static Pattern PATTERN_ENGLISH_UPPER                    = Pattern.compile("^[A-Za-z]+$");
    /**
     * 常用正则表达式：匹配大写英文
     */
    public final static Pattern PATTERN_ENGLISH_LOWER                    = Pattern.compile("^[a-z]+$");
    /**
     * 常用正则表达式：匹配英文和数字
     */
    public final static Pattern PATTERN_ENGLISH_NUMBER                   = Pattern.compile("^[A-Za-z0-9]+$");
    /**
     * 常用正则表达式：匹配中文、数字、英文
     */
    public final static Pattern PATTERN_CHINESE_ENGLISH_NUMBER           = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z0-9]+$");
    /**
     * 常用正则表达式：匹配数字、英文、下划线
     */
    public final static Pattern PATTERN_ENGLISH_NUMBER_UNDERLINE         = Pattern.compile("^\\w+$");
    /**
     * 常用正则表达式：匹配中文、数字、英文、下划线
     */
    public final static Pattern PATTERN_CHINESE_ENGLISH_NUMBER_UNDERLINE = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z0-9_]+$");
    /**
     * 正则表达式：长度为n-m的所有字符
     */
    public static final String  PATTERN_MIN_MAX_CHARACTER                = "^.{{},{}}$";

    // ------------ 校验特定场景 ------------

    /**
     * 常用正则表达式：匹配Email地址
     */
    public final static Pattern PATTERN_EMAIL             = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    /**
     * 常用正则表达式：匹配域名
     */
    public final static Pattern PATTERN_HOST              = Pattern.compile("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.?");
    /**
     * 常用正则表达式：匹配URL
     */
    public final static Pattern PATTERN_URL               = Pattern.compile("[a-zA-z]+://[^\\s]*");
    /**
     * 常用正则表达式：匹配手机号码
     */
    public final static Pattern PATTERN_MOBILE            = Pattern.compile("^([1][3,4,5,6,7,8,9])\\d{9}$");
    /**
     * 常用正则表达式：匹配国内电话号码
     */
    public final static Pattern PATTERN_CHINESE_TELEPHONE = Pattern.compile("\\d{3}-\\d{8}|\\d{4}-\\d{7}");
    /**
     * 常用正则表达式：简单匹配身份证号，如果要严格验证，使用ReIdCards
     */
    public final static Pattern PATTERN_CREDIT_ID         = Pattern.compile("^((\\d{18})|([0-9x]{18})|([0-9X]{18}))$");
    /**
     * 常用正则表达式：匹配IP
     */
    public final static Pattern PATTERN_IP                = Pattern.compile("((?:(?:25[0-5]|2[0-4]\\\\d|[01]?\\\\d?\\\\d)\\\\.){3}(?:25[0-5]|2[0-4]\\\\d|[01]?\\\\d?\\\\d))");
    /**
     * 常用正则表达式：匹配腾讯QQ号
     */
    public final static Pattern PATTERN_QQ                = Pattern.compile("[1-9][0-9]{4,}");
    /**
     * 常用正则表达式：匹配中国邮政编码
     */
    public final static Pattern PATTERN_CHINESE_POSTCODE  = Pattern.compile("[1-9]\\d{5}(?!\\d)");
    /**
     * 常用正则表达式：匹配XML
     */
    public final static Pattern PATTERN_XML               = Pattern.compile("^([a-zA-Z]+-?)+[a-zA-Z0-9]+\\\\.[x|X][m|M][l|L]$");

}
