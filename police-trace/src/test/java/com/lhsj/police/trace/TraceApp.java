package com.lhsj.police.trace;

public class TraceApp {

    public static void main(String[] args) {
        group();
    }

    private static void simple() {
        Traces.start()
                .app("app")
                .type("traceTest")
                .request("request")
                .response("response")
                .traceId("traceId")
                .desc("desc")
                .success()
                .key("key")
                .log();
    }

    private static void group() {
        Traces.start()
                .key("key")
                .log();

        Traces.start()
                .key("key")
                .group1("1")
                .log();

        Traces.start()
                .key("key")
                .group2("2")
                .log();

        Traces.start()
                .app("app")
                .type("traceTest")
                .request("request")
                .response("response")
                .traceId("traceId")
                .desc("desc")
                .success()
                .key("key")
                .group1("1")
                .group2("2")
                .group3("3")
                .log();
    }
}
