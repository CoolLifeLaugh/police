package com.lhsj.police.core.base;

import com.lhsj.police.core.io.ReUrlResources;
import com.lhsj.police.core.number.ReNumbers;
import com.lhsj.police.core.text.ReCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

/**
 * 关于Properties的工具类
 * <p>
 * 1. 统一风格读取Properties到各种数据类型
 * <p>
 * 2. 从文件或字符串装载Properties
 */
public class Properties {

    private static Logger logger = LoggerFactory.getLogger(Properties.class);

    /////////////////// 读取Properties ////////////////////

    public static Boolean getBoolean(java.util.Properties p, String name, Boolean defaultValue) {
        return ReBooleans.toBooleanObject(p.getProperty(name), defaultValue);
    }

    public static Integer getInt(java.util.Properties p, String name, Integer defaultValue) {
        return ReNumbers.toIntObject(p.getProperty(name), defaultValue);
    }

    public static Long getLong(java.util.Properties p, String name, Long defaultValue) {
        return ReNumbers.toLongObject(p.getProperty(name), defaultValue);
    }

    public static Double getDouble(java.util.Properties p, String name, Double defaultValue) {
        return ReNumbers.toDoubleObject(p.getProperty(name), defaultValue);
    }

    public static String getString(java.util.Properties p, String name, String defaultValue) {
        return p.getProperty(name, defaultValue);
    }

    /////////// 加载Properties////////

    /**
     * 从文件路径加载properties. 默认使用utf-8编码解析文件
     * <p>
     * 路径支持从外部文件或resources文件加载, "file://"或无前缀代表外部文件, "classpath:"代表resources
     */
    public static java.util.Properties loadFromFile(String generalPath) {
        java.util.Properties p = new java.util.Properties();
        try (Reader reader = new InputStreamReader(ReUrlResources.asStream(generalPath), ReCharsets.UTF_8)) {
            p.load(reader);
        } catch (IOException e) {
            logger.warn("Load property from " + generalPath + " failed", e);
        }
        return p;
    }

    /**
     * 从字符串内容加载Properties
     */
    public static java.util.Properties loadFromString(String content) {
        java.util.Properties p = new java.util.Properties();
        try (Reader reader = new StringReader(content)) {
            p.load(reader);
        } catch (IOException ignored) { // NOSONAR
        }
        return p;
    }

}
