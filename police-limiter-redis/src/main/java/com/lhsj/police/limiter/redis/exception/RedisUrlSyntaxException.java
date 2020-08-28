package com.lhsj.police.limiter.redis.exception;

/**
 * Exception thrown when a Redis URL is malformed or invalid.
 *
 * @author Scott Frederick
 */
public class RedisUrlSyntaxException extends RuntimeException {

    private final String url;

    public RedisUrlSyntaxException(String url, Exception cause) {
        super(buildMessage(url), cause);
        this.url = url;
    }

    public RedisUrlSyntaxException(String url) {
        super(buildMessage(url));
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    private static String buildMessage(String url) {
        return "Invalid Redis URL '" + url + "'";
    }

}

