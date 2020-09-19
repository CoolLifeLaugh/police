package com.lhsj.police.trace;

import com.lhsj.police.core.concurrent.NamedThreadLocal;
import com.lhsj.police.core.text.ReStrings;
import com.lhsj.police.trace.global.TraceGlobal;
import com.lhsj.police.trace.model.TraceLog;

import java.util.Date;

import static java.util.Optional.ofNullable;

@SuppressWarnings("unused")
public final class Traces {

    public static TraceLog start() {
        return TraceLog.of();
    }

    // ------------- thread local ---------------

    private final static ThreadLocal<TraceLog> localTraceLog = new NamedThreadLocal<>("localTraceLog");

    private final static ThreadLocal<String> localTraceId = new NamedThreadLocal<>("localTraceId");

    public static TraceLog getLocalTraceLog() {
        return localTraceLog.get();
    }

    public static void setLocalTraceLog(TraceLog traceLog) {
        localTraceLog.set(traceLog);
    }

    public static void removeLocalTraceLog() {
        localTraceLog.remove();
    }

    public static String getLocalTraceId() {
        return localTraceId.get();
    }

    public static void setLocalTraceId(String traceId) {
        localTraceId.set(traceId);
    }

    public static void removeLocalTraceId() {
        localTraceId.remove();
    }

    // ------------- tools ---------------

    public static void app(String value) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.app(value));
    }

    public static void app(TraceLog trace, String value) {
        ofNullable(trace).ifPresent(e -> e.app(value));
    }

    public static void ip(String value) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.ip(value));
    }

    public static void ip(TraceLog trace, String value) {
        ofNullable(trace).ifPresent(e -> e.ip(value));
    }

    public static void traceId(String value) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.traceId(value));
    }

    public static void traceId(TraceLog trace, String value) {
        ofNullable(trace).ifPresent(e -> e.traceId(value));
    }

    public static void type(String value) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.type(value));
    }

    public static void type(TraceLog trace, String value) {
        ofNullable(trace).ifPresent(e -> e.type(value));
    }

    public static void result(String value) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.result(value));
    }

    public static void resultSuccess() {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.result(TraceGlobal.RESULT_SUCCESS));
    }

    public static void resultFail() {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.result(TraceGlobal.RESULT_FAIL));
    }

    public static void result(TraceLog trace, String value) {
        ofNullable(trace).ifPresent(e -> e.result(value));
    }

    public static void resultSuccess(TraceLog trace) {
        ofNullable(trace).ifPresent(e -> e.result(TraceGlobal.RESULT_SUCCESS));
    }

    public static void resultFail(TraceLog trace) {
        ofNullable(trace).ifPresent(e -> e.result(TraceGlobal.RESULT_FAIL));
    }

    public static void gmtStart(Date value) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.gmtStart(value));
    }

    public static void gmtStart(TraceLog trace, Date value) {
        ofNullable(trace).ifPresent(e -> e.gmtStart(value));
    }

    public static void gmtEnd(Date value) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.gmtEnd(value));
    }

    public static void gmtEnd(TraceLog trace, Date value) {
        ofNullable(trace).ifPresent(e -> e.gmtEnd(value));
    }

    public static void costStart() {
        ofNullable(getLocalTraceLog()).ifPresent(TraceLog::costStart);
    }

    public static void costStart(TraceLog trace) {
        ofNullable(trace).ifPresent(TraceLog::costStart);
    }

    public static void costEnd() {
        ofNullable(getLocalTraceLog()).ifPresent(TraceLog::costEnd);
    }

    public static void costEnd(TraceLog trace) {
        ofNullable(trace).ifPresent(TraceLog::costEnd);
    }

    public static void cost(Long value) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.cost(value));
    }

    public static void cost(TraceLog trace, Long value) {
        ofNullable(trace).ifPresent(e -> e.cost(value));
    }

    public static void request(String value) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.request(value));
    }

    public static void request(TraceLog trace, String value) {
        ofNullable(trace).ifPresent(e -> e.request(value));
    }

    public static void request(String format, Object... argArray) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.request(ReStrings.format(format, argArray)));
    }

    public static void request(TraceLog trace, String format, Object... argArray) {
        ofNullable(trace).ifPresent(e -> trace.request(ReStrings.format(format, argArray)));
    }

    public static void response(String value) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.response(value));
    }

    public static void response(TraceLog trace, String value) {
        ofNullable(trace).ifPresent(e -> e.response(value));
    }

    public static void response(String format, Object... argArray) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.response(ReStrings.format(format, argArray)));
    }

    public static void response(TraceLog trace, String format, Object... argArray) {
        ofNullable(trace).ifPresent(e -> trace.response(ReStrings.format(format, argArray)));
    }

    public static void key(String value) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.key(value));
    }

    public static void key(TraceLog trace, String value) {
        ofNullable(trace).ifPresent(e -> e.key(value));
    }

    public static void key(String format, Object... argArray) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.key(ReStrings.format(format, argArray)));
    }

    public static void key(TraceLog trace, String format, Object... argArray) {
        ofNullable(trace).ifPresent(e -> trace.key(ReStrings.format(format, argArray)));
    }

    public static void thread(String value) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.thread(value));
    }

    public static void thread(TraceLog trace, String value) {
        ofNullable(trace).ifPresent(e -> e.thread(value));
    }

    public static void desc(String value) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.desc(value));
    }

    public static void desc(Throwable ex) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> {
            e.fail();
            e.desc(ex);
        });
    }

    public static void desc(TraceLog trace, String value) {
        ofNullable(trace).ifPresent(e -> e.desc(value));
    }

    public static void desc(TraceLog trace, Throwable ex) {
        ofNullable(trace).ifPresent(e -> {
            e.fail();
            e.desc(ex);
        });
    }

    public static void desc(String format, Object... argArray) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.desc(ReStrings.format(format, argArray)));
    }

    public static void desc(TraceLog trace, String format, Object... argArray) {
        ofNullable(trace).ifPresent(e -> trace.desc(ReStrings.format(format, argArray)));
    }

    public static void group1(TraceLog trace, String group) {
        ofNullable(trace).ifPresent(e -> e.group1(group));
    }

    public static void group1(String group) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.group1(group));
    }

    public static void group2(TraceLog trace, String group) {
        ofNullable(trace).ifPresent(e -> e.group2(group));
    }

    public static void group2(String group) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.group2(group));
    }

    public static void group3(TraceLog trace, String group) {
        ofNullable(trace).ifPresent(e -> e.group3(group));
    }

    public static void group3(String group) {
        ofNullable(getLocalTraceLog()).ifPresent(e -> e.group3(group));
    }
}
