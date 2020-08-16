package com.lhsj.police.trace;

public class TraceApp {

    public static void main(String[] args) {
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
}
