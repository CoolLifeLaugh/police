package com.lhsj.police.trace.command;

import com.lhsj.police.core.text.ReStrings;
import com.lhsj.police.trace.global.TraceGlobal;
import com.lhsj.police.trace.model.TraceLog;

import java.util.Date;

import static java.util.Optional.ofNullable;

@SuppressWarnings("unused")
public class TraceCommand {

    private TraceLog log;

    public TraceLog getLog() {
        return log;
    }

    public void setLog(TraceLog log) {
        this.log = log;
    }

    public TraceCommand() {
    }

    public TraceCommand(TraceLog log) {
        this.log = log;
    }

    public static TraceCommand of() {
        TraceCommand command = new TraceCommand();
        command.setLog(TraceGlobal.localTraceLog.get());
        return command;
    }

    public TraceCommand of(TraceLog log) {
        return new TraceCommand(log);
    }

    public TraceCommand app(String value) {
        ofNullable(log).ifPresent(e -> e.app(value));
        return this;
    }

    public TraceCommand ip(String value) {
        ofNullable(log).ifPresent(e -> e.ip(value));
        return this;
    }

    public TraceCommand traceId(String value) {
        ofNullable(log).ifPresent(e -> e.traceId(value));
        return this;
    }

    public TraceCommand type(String value) {
        ofNullable(log).ifPresent(e -> e.type(value));
        return this;
    }

    public TraceCommand result(String value) {
        ofNullable(log).ifPresent(e -> e.result(value));
        return this;
    }

    public TraceCommand resultSuccess() {
        ofNullable(log).ifPresent(e -> e.result(TraceGlobal.RESULT_SUCCESS));
        return this;
    }

    public TraceCommand resultFail() {
        ofNullable(log).ifPresent(e -> e.result(TraceGlobal.RESULT_FAIL));
        return this;
    }

    public TraceCommand gmtStart(Date value) {
        ofNullable(log).ifPresent(e -> e.gmtStart(value));
        return this;
    }

    public TraceCommand gmtEnd(Date value) {
        ofNullable(log).ifPresent(e -> e.gmtEnd(value));
        return this;
    }

    public TraceCommand costStart() {
        ofNullable(log).ifPresent(TraceLog::costStart);
        return this;
    }

    public TraceCommand costEnd() {
        ofNullable(log).ifPresent(TraceLog::costEnd);
        return this;
    }

    public TraceCommand cost(Long value) {
        ofNullable(log).ifPresent(e -> e.cost(value));
        return this;
    }

    public TraceCommand request(String value) {
        ofNullable(log).ifPresent(e -> e.request(value));
        return this;
    }

    public TraceCommand request(String format, Object... argArray) {
        ofNullable(log).ifPresent(e -> e.request(ReStrings.format(format, argArray)));
        return this;
    }

    public TraceCommand response(String value) {
        ofNullable(log).ifPresent(e -> e.response(value));
        return this;
    }

    public TraceCommand response(String format, Object... argArray) {
        ofNullable(log).ifPresent(e -> e.response(ReStrings.format(format, argArray)));
        return this;
    }

    public TraceCommand key(String value) {
        ofNullable(log).ifPresent(e -> e.key(value));
        return this;
    }

    public TraceCommand key(String format, Object... argArray) {
        ofNullable(log).ifPresent(e -> e.key(ReStrings.format(format, argArray)));
        return this;
    }

    public TraceCommand thread(String value) {
        ofNullable(log).ifPresent(e -> e.thread(value));
        return this;
    }

    public TraceCommand desc(String value) {
        ofNullable(log).ifPresent(e -> e.desc(value));
        return this;
    }

    public TraceCommand desc(Throwable ex) {
        ofNullable(log).ifPresent(e -> {
            e.fail();
            e.desc(ex);
        });
        return this;
    }

    public TraceCommand desc(String format, Object... argArray) {
        ofNullable(log).ifPresent(e -> e.desc(ReStrings.format(format, argArray)));
        return this;
    }

    public TraceCommand group1(String group) {
        ofNullable(log).ifPresent(e -> e.group1(group));
        return this;
    }

    public TraceCommand group2(String group) {
        ofNullable(log).ifPresent(e -> e.group2(group));
        return this;
    }

    public TraceCommand group3(String group) {
        ofNullable(log).ifPresent(e -> e.group3(group));
        return this;
    }
}
