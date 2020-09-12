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

import static com.lhsj.police.core.constant.Constants.EMPTY;
import static com.lhsj.police.core.time.ReClocks.currentDate;
import static com.lhsj.police.core.time.ReDateFormats.formatDateTimeMillis;
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
    public final static  String RESULT_SUCCESS  = "success";
    public final static  String RESULT_FAIL     = "fail";
    private final static String BIG_SEPARATOR   = "|";
    private final static String SMALL_SEPARATOR = ",";

    public static String app;
    public static String ip;

    private String       traceId;
    private String       type;
    private String       result;
    private Date         gmtStart  = currentDate();
    private Date         gmtEnd;
    private Long         costStart = currentTimeMillis();
    private Long         costEnd;
    private Long         cost;
    private String       request;
    private String       response;
    private List<String> keys;
    private String       thread;
    private String       desc;

    public void log() {
        ofNullable(TraceGlobal.logger).ifPresent(e -> e.info(format()));
    }

    public String format() {
        return Joiner.on(BIG_SEPARATOR).useForNull(EMPTY)
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
                .orElse(null);
    }

    private String formatKeys() {
        if (isEmpty(keys)) {
            return null;
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
}
