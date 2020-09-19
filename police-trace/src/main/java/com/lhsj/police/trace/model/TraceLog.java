package com.lhsj.police.trace.model;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.lhsj.police.core.base.ReExceptions;
import com.lhsj.police.core.exception.AbstractCodeException;
import com.lhsj.police.core.text.ReStrings;
import com.lhsj.police.trace.global.TraceGlobal;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;
import static com.lhsj.police.core.constant.Constants.EMPTY;
import static com.lhsj.police.core.time.ReClocks.currentDate;
import static com.lhsj.police.core.time.ReDateFormats.formatDateTimeMillis;
import static com.lhsj.police.trace.global.TraceGlobal.RESULT_FAIL;
import static com.lhsj.police.trace.global.TraceGlobal.RESULT_SUCCESS;
import static com.lhsj.police.trace.global.TraceGlobal.bingoPredicate;
import static java.lang.System.currentTimeMillis;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.replaceChars;

@SuppressWarnings({"unused"})
public class TraceLog implements Serializable {

    private final static String REPLACE_FROM = "\n|", REPLACE_TO = "n_n";
    private final static String       BIG_SEPARATOR       = "|";
    private final static String       SMALL_SEPARATOR     = ",";
    private final static List<String> BLANK_GROUPS        = newArrayList(EMPTY, EMPTY, EMPTY);
    private final static String       BLANK_GROUPS_FORMAT = Joiner.on(BIG_SEPARATOR).useForNull(EMPTY).join(BLANK_GROUPS);

    /**
     * 应用名
     */
    public static String       app;
    /**
     * 本机IP地址
     */
    public static String       ip;
    /**
     * 跟踪ID
     */
    private       String       traceId;
    /**
     * 日志类型 (最好有值)
     */
    private       String       type;
    /**
     * 返回值编码 {@link TraceGlobal RESULT_SUCCESS, RESULT_FAIL}
     */
    private       String       result;
    /**
     * 开始时间
     */
    private       Date         gmtStart  = currentDate();
    /**
     * 结束时间
     */
    private       Date         gmtEnd;
    /**
     * 开始时间戳
     */
    private       Long         costStart = currentTimeMillis();
    /**
     * 结束时间戳
     */
    private       Long         costEnd;
    /**
     * 耗时
     */
    private       Long         cost;
    /**
     * 请求参数
     */
    private       String       request;
    /**
     * 响应参数
     */
    private       String       response;
    /**
     * 关键字
     */
    private       List<String> keys;
    /**
     * 当前线程名
     */
    private       String       thread;
    /**
     * 描述字段，可以写入任意字符串
     */
    private       String       desc;
    /**
     * 统计用的扩展属性，API上默认支持3个，每一个group，都会新增一个新的分隔符
     */
    private       List<String> groups;

    public void log() {
        ofNullable(TraceGlobal.logger).ifPresent(e -> e.info(format()));
    }

    public String format() {
        return Joiner.on(BIG_SEPARATOR)
                .useForNull(EMPTY)
                .join(
                        formatApp()
                        , formatIp()
                        , traceId
                        , type
                        , result
                        , formatGmtStart()
                        , formatGmtEnd()
                        , formatCost()
                        , request
                        , response
                        , formatKeys()
                        , formatThread()
                        , formatDesc()
                        , formatGroups()
                );
    }

    private String formatApp() {
        if (isNotBlank(app)) {
            return app;
        }

        return TraceGlobal.app;
    }

    private String formatIp() {
        if (isNotBlank(ip)) {
            return ip;
        }

        return TraceGlobal.ip;
    }

    private String formatGmtStart() {
        if (nonNull(gmtStart)) {
            return formatDateTimeMillis(gmtStart);
        } else {
            return formatDateTimeMillis(currentDate());
        }
    }

    private String formatGmtEnd() {
        if (nonNull(gmtEnd)) {
            return formatDateTimeMillis(gmtEnd);
        } else {
            return formatDateTimeMillis(currentDate());
        }
    }

    private Long formatCost() {
        if (nonNull(cost)) {
            return cost;
        }

        if (allNotNull(costStart, costEnd)) {
            return costEnd - costStart;
        }

        return ofNullable(costStart)
                .map(e -> currentTimeMillis() - e)
                .orElse(0L);
    }

    private String formatKeys() {
        if (isEmpty(keys)) {
            return EMPTY;
        }
        return Joiner.on(SMALL_SEPARATOR).useForNull(EMPTY).join(keys);
    }

    private boolean isBingo(Predicate<String> predicate) {
        return ofNullable(predicate)
                .filter(e -> isNotBlank(traceId))
                .map(e -> predicate.test(traceId))
                .orElse(false);
    }

    private String formatThread() {
        if (isNotBlank(thread)) {
            return thread;
        }
        return Thread.currentThread().getName();
    }

    private String formatDesc() {
        return isBingo(bingoPredicate) ? "__bingo__," + this.desc : this.desc;
    }

    private String formatGroups() {
        if (isEmpty(groups)) {
            return Joiner.on(BIG_SEPARATOR)
                    .useForNull(EMPTY)
                    .join(BLANK_GROUPS);
        }

        return Joiner.on(BIG_SEPARATOR)
                .useForNull(EMPTY)
                .join(groups);
    }

    // ---------------- flow api ----------------

    public static TraceLog of() {
        return new TraceLog();
    }

    public TraceLog app(String app) {
        TraceLog.app = app;
        return this;
    }

    public TraceLog ip(String ip) {
        TraceLog.ip = ip;
        return this;
    }

    public TraceLog traceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public TraceLog type(String type) {
        this.type = type;
        return this;
    }

    public TraceLog result(String result) {
        this.result = result;
        return this;
    }

    public TraceLog success() {
        this.result = RESULT_SUCCESS;
        return this;
    }

    public TraceLog successIfNull() {
        if (isNull(result)) {
            this.result = RESULT_SUCCESS;
        }
        return this;
    }

    public TraceLog success(Boolean success) {
        if (ofNullable(success).orElse(false)) {
            success();
        } else {
            fail();
        }
        return this;
    }

    public TraceLog fail() {
        this.result = RESULT_FAIL;
        return this;
    }

    public TraceLog failIfNull() {
        if (isNull(result)) {
            this.result = RESULT_FAIL;
        }
        return this;
    }

    public TraceLog gmtStart(Date gmtStart) {
        this.gmtStart = gmtStart;
        return this;
    }

    public TraceLog gmtEnd(Date gmtEnd) {
        this.gmtEnd = gmtEnd;
        return this;
    }

    public TraceLog costStart() {
        this.costStart = currentTimeMillis();
        return this;
    }

    public TraceLog costEnd() {
        this.costEnd = currentTimeMillis();
        return this;
    }

    public TraceLog cost() {
        if (isNull(costStart)) {
            return this;
        }

        this.cost = currentTimeMillis() - costStart;
        return this;
    }

    public TraceLog cost(Long cost) {
        this.cost = cost;
        return this;
    }

    public TraceLog request(String request) {
        if (isBlank(request)) {
            return this;
        }
        this.request = replaceChars(request, REPLACE_FROM, REPLACE_TO);
        return this;
    }

    public TraceLog request(String format, Object... args) {
        if (isBlank(format)) {
            return this;
        }
        request(ReStrings.format(format, args));
        return this;
    }

    public TraceLog response(String response) {
        if (isBlank(response)) {
            return this;
        }
        this.response = replaceChars(response, REPLACE_FROM, REPLACE_TO);
        return this;
    }

    public TraceLog response(String format, Object... args) {
        if (isBlank(format)) {
            return this;
        }
        response(ReStrings.format(format, args));
        return this;
    }

    public TraceLog key(String key) {
        if (isNull(keys)) {
            keys = Lists.newArrayList(key);
        } else {
            keys.add(key);
        }
        return this;
    }

    public TraceLog key(String format, Object... args) {
        if (isNull(keys)) {
            keys = Lists.newArrayList(ReStrings.format(format, args));
        } else {
            keys.add(ReStrings.format(format, args));
        }
        return this;
    }

    public TraceLog keys(List<String> keys) {
        if (isEmpty(keys)) {
            return this;
        }

        if (isNull(this.keys)) {
            this.keys = Lists.newArrayList(keys);
        } else {
            this.keys.addAll(keys);
        }
        return this;
    }

    public TraceLog thread(String thread) {
        if (isBlank(thread)) {
            return this;
        }
        this.thread = thread;
        return this;
    }

    public TraceLog desc(String desc) {
        if (isBlank(desc)) {
            return this;
        }
        this.desc = replaceChars(desc, REPLACE_FROM, REPLACE_TO);
        return this;
    }

    public TraceLog desc(String format, Object... args) {
        if (isBlank(format)) {
            return this;
        }
        desc(ReStrings.format(format, args));
        return this;
    }

    public TraceLog desc(Throwable ex) {
        ofNullable(ex)
                .map(exceptionDescFunc)
                .filter(StringUtils::isNotBlank)
                .ifPresent(this::desc);

        return this;
    }

    private static Function<Throwable, String> exceptionDescFunc = ex -> {
        if (isNull(ex)) {
            return EMPTY;
        }

        if (ex instanceof AbstractCodeException) {
            AbstractCodeException cex = (AbstractCodeException) ex;
            return ReStrings.format("{}, code = {}, message = {}",
                    ex.getClass().getName(),
                    cex.getCode(),
                    ReExceptions.simpleTraceText(cex, 16));
        } else {
            return ReStrings.format("{}, message = {}",
                    ex.getClass().getName(),
                    ReExceptions.simpleTraceText(ex, 16));
        }
    };

    public TraceLog group1(String group) {
        if (isBlank(group)) {
            return this;
        }

        if (isEmpty(groups)) {
            groups = BLANK_GROUPS;
        }

        groups.set(0, group);
        return this;
    }

    public TraceLog group2(String group) {
        if (isBlank(group)) {
            return this;
        }

        if (isEmpty(groups)) {
            groups = BLANK_GROUPS;
        }

        groups.set(1, group);
        return this;
    }

    public TraceLog group3(String group) {
        if (isBlank(group)) {
            return this;
        }

        if (isEmpty(groups)) {
            groups = BLANK_GROUPS;
        }

        groups.set(2, group);
        return this;
    }

    // ---------------- getter ----------------


    public static String getApp() {
        return app;
    }

    public static String getIp() {
        return ip;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getType() {
        return type;
    }

    public String getResult() {
        return result;
    }

    public Date getGmtStart() {
        return gmtStart;
    }

    public Date getGmtEnd() {
        return gmtEnd;
    }

    public Long getCostStart() {
        return costStart;
    }

    public Long getCostEnd() {
        return costEnd;
    }

    public Long getCost() {
        return cost;
    }

    public String getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    }

    public List<String> getKeys() {
        return keys;
    }

    public String getThread() {
        return thread;
    }

    public String getDesc() {
        return desc;
    }

    public List<String> getGroups() {
        return groups;
    }
}
