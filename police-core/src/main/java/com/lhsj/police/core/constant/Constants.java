package com.lhsj.police.core.constant;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;

/**
 * org.springframework.http.HttpStatus
 */
public class Constants {

    // 此处描述全局通用的常量

    public static final String COMMA        = ",";
    public static final String COLON        = ":";
    public static final String DOLLAR       = "$";
    public static final String DOUDLE_COLON = "::";
    public static final String UNDERLINE    = "_";
    public static final String TRUE         = "true";
    public static final String FALSE        = "false";
    public static final String EMPTY        = "";
    public static final String BLANK        = " ";

    // 此处描述请求相应码

    public static final String CODE_SUCCESS     = "200";
    public static final String MSG_SUCCESS      = "request success";
    public static final String CODE_ERROR       = "500";
    public static final String MSG_ERROR        = "request error ";
    public static final String CODE_PARAM_ERROR = "600";
    public static final String MSG_PARAM_ERROR  = "param error";

    // 此处描述SQL操作类型

    public static final String SELECT = "select";
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";

    // 此处描述方法名

    public static final String GET = "get";
    public static final String SET = "set";

    // NETWORK

    public static final List<String> LOCALHOSTS = Lists.newArrayList("127.0.0.1", "localhost");

    // ------------ JVM相关常量 ------------

    /**
     * JVM vendor info.
     */
    public static final String JVM_VENDOR  = System.getProperty("java.vm.vendor");
    public static final String JVM_VERSION = System.getProperty("java.vm.version");
    public static final String JVM_NAME    = System.getProperty("java.vm.name");

    public static final String OS_ARCH     = System.getProperty("os.arch");
    public static final String OS_VERSION  = System.getProperty("os.version");
    public static final String JAVA_VENDOR = System.getProperty("java.vendor");

    /**
     * The value of <tt>System.getProperty("java.version")</tt>.
     **/
    public static final String JAVA_VERSION = System.getProperty("java.version");

    /**
     * True iff running on a 64bit JVM
     */
    public static final boolean JRE_IS_64BIT;

    static {
        boolean is64Bit;
        try {
            final Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            final Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            final Object unsafe = unsafeField.get(null);
            final int addressSize = ((Number) unsafeClass.getMethod("addressSize").invoke(unsafe)).intValue();
            is64Bit = addressSize >= 8;
        } catch (Exception e) {
            final String x = System.getProperty("sun.arch.data.model");
            if (x != null) {
                is64Bit = x.contains("64");
            } else {
                is64Bit = OS_ARCH != null && OS_ARCH.contains("64");
            }
        }
        JRE_IS_64BIT = is64Bit;
    }
}
