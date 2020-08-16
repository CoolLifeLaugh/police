package com.lhsj.police.core.text;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.base.Utf8;
import com.lhsj.police.core.annotation.Nullable;
import com.lhsj.police.core.collection.ReLists;
import com.lhsj.police.core.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * 尽量使用Common Lang StringUtils, 基本覆盖了所有类库的StringUtils
 * <p>
 * 本类仅补充少量额外方法, 尤其是针对char的运算
 * <p>
 * 1. split char/chars
 * <p>
 * 2. 针对char的replace first/last, startWith,endWith 等
 *
 * @author calvin
 */
public class ReStrings {

    /////////// split char 相关 ////////

    /**
     * 高性能的Split，针对char的分隔符号，比JDK String自带的高效.
     * <p>
     * copy from Commons Lange 3.5 StringUtils 并做优化
     *
     * @see #split(String, char, int)
     */
    public static List<String> split(@Nullable final String str, final char separatorChar) {
        return split(str, separatorChar, 10);
    }

    /**
     * 高性能的Split，针对char的分隔符号，比JDK String自带的高效.
     * <p>
     * copy from Commons Lange 3.5 StringUtils, 做如下优化:
     * <p>
     * 1. 最后不做数组转换，直接返回List.
     * <p>
     * 2. 可设定List初始大小.
     * <p>
     * 3. preserveAllTokens 取默认值false
     *
     * @param expectParts 预估分割后的List大小，初始化数据更精准
     * @return 如果为null返回null, 如果为""返回空数组
     */
    public static List<String> split(@Nullable final String str, final char separatorChar, int expectParts) {
        if (str == null) {
            return null;
        }

        final int len = str.length();
        if (len == 0) {
            return ReLists.emptyList();
        }

        final List<String> list = new ArrayList<String>(expectParts);
        int i = 0;
        int start = 0;
        boolean match = false;
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (match) {
                    list.add(str.substring(start, i));
                    match = false;
                }
                start = ++i;
                continue;
            }
            match = true;
            i++;
        }
        if (match) {
            list.add(str.substring(start, i));
        }
        return list;
    }

    /**
     * 使用多个可选的char作为分割符, 还可以设置omitEmptyStrings,trimResults等配置
     * <p>
     * 设置后的Splitter进行重用，不要每次创建
     *
     * @param separatorChars 比如Unix/Windows的路径分割符 "/\\"
     * @see Splitter
     */
    public static Splitter charsSplitter(final String separatorChars) {
        return Splitter.on(CharMatcher.anyOf(separatorChars));
    }

    ////////// 其他 char 相关 ///////////

    /**
     * String 有replace(char,char)，但缺少单独replace first/last的
     */
    public static String replaceFirst(@Nullable String s, char sub, char with) {
        if (s == null) {
            return null;
        }
        int index = s.indexOf(sub);
        if (index == -1) {
            return s;
        }
        char[] str = s.toCharArray();
        str[index] = with;
        return new String(str);
    }

    /**
     * String 有replace(char,char)替换全部char，但缺少单独replace first/last
     */
    public static String replaceLast(@Nullable String s, char sub, char with) {
        if (s == null) {
            return null;
        }

        int index = s.lastIndexOf(sub);
        if (index == -1) {
            return s;
        }
        char[] str = s.toCharArray();
        str[index] = with;
        return new String(str);
    }

    /**
     * 判断字符串是否以字母开头
     * <p>
     * 如果字符串为Null或空，返回false
     */
    public static boolean startWith(@Nullable CharSequence s, char c) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        return s.charAt(0) == c;
    }

    /**
     * 判断字符串是否以字母结尾
     * <p>
     * 如果字符串为Null或空，返回false
     */
    public static boolean endWith(@Nullable CharSequence s, char c) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        return s.charAt(s.length() - 1) == c;
    }

    /**
     * 如果结尾字符为c, 去除掉该字符.
     */
    public static String removeEnd(final String s, final char c) {
        if (endWith(s, c)) {
            return s.substring(0, s.length() - 1);
        }
        return s;
    }

    ///////////// 其他 ////////////

    /**
     * 计算字符串被UTF8编码后的字节数 via guava
     *
     * @see Utf8#encodedLength(CharSequence)
     */
    public static int utf8EncodedLength(@Nullable CharSequence sequence) {
        if (StringUtils.isEmpty(sequence)) {
            return 0;
        }
        return Utf8.encodedLength(sequence);
    }

    public static boolean notEquals(final CharSequence cs1, final CharSequence cs2) {
        return !StringUtils.equals(cs1, cs2);
    }

    // --------------------------------------------------

    public static int indexOfIgnoreCase(String s, String key) {
        if (com.google.common.base.Strings.isNullOrEmpty(s) || com.google.common.base.Strings.isNullOrEmpty(key)) {
            return -1;
        }
        return s.toLowerCase().indexOf(key.toLowerCase());
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }

        int length = str.length();

        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 提取字符里的第一个数字
     *
     * @param s
     * @return
     */
    public static Integer extractFirstInt(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            final String word = s.substring(i, i + 1);
            if (isNumeric(word)) {
                sb.append(word);
            } else {
                break;
            }
        }
        final String ret = sb.toString();
        return isNumeric(s) ? Integer.parseInt(ret) : null;
    }

    public static boolean isNonEmpty(String... strs) {
        if (strs == null || strs.length == 0) {
            return false;
        }
        for (String s : strs) {
            if (s == null || s.length() == 0) {
                return false;
            }
        }
        return true;
    }

    public static String subStr(String s, int limit) {
        if (com.google.common.base.Strings.isNullOrEmpty(s)) {
            return Constants.EMPTY;
        }
        final int len = s.length();
        if (len <= limit) {
            return s;
        }
        return s.substring(0, limit);
    }

    public static String format(final String format, final Object... values) {
        return MessageFormatter.arrayFormat(format, values).getMessage();
    }
}
