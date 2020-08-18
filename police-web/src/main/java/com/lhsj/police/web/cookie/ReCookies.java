package com.lhsj.police.web.cookie;

import com.lhsj.police.core.base.ReValidates;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public final class ReCookies {

    public static String readCookie(HttpServletRequest request, String name) {
        ReValidates.isTrue(nonNull(request), "request is null");
        ReValidates.isTrue(isNotBlank(name), "name is null");

        return Arrays.stream(ofNullable(request.getCookies())
                .orElse(new Cookie[0]))
                .filter(e -> Objects.equals(name, e.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    public static Cookie writeCookie(String domain, String key, String value) {
        ReValidates.isTrue(isNotBlank(domain), "domain is null");
        ReValidates.isTrue(isNotBlank(key), "key is null");
        ReValidates.isTrue(isNotBlank(value), "value is null");

        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(domain);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        return cookie;
    }

    public static Cookie deleteCookie(String domain, String key) {
        ReValidates.isTrue(isNotBlank(domain), "domain is null");
        ReValidates.isTrue(isNotBlank(key), "key is null");

        Cookie cookie = new Cookie(key, "");
        cookie.setDomain(domain);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }

    public static void addCookie(HttpServletResponse response, Cookie cookie) {
        addCookie(response, cookie, true);
    }

    public static void addCookie(HttpServletResponse response, Cookie cookie, boolean isHttpOnly) {
        ReValidates.isTrue(nonNull(response), "response is null");
        ReValidates.isTrue(nonNull(cookie), "cookie is null");

        final String name = cookie.getName();
        final String value = cookie.getValue();
        final int maxAge = cookie.getMaxAge();
        final String path = cookie.getPath();
        final String domain = cookie.getDomain();
        final boolean isSecure = cookie.getSecure();

        StringBuilder buffer = new StringBuilder();
        buffer.append(name).append("=").append(value).append(";");

        if (maxAge == 0) {
            buffer.append("Expires=Thu Jan 01 08:00:00 CST 1970;");
        } else if (maxAge > 0) {
            buffer.append("Max-Age=").append(maxAge).append(";");
        }

        if (isNotBlank(domain)) {
            buffer.append("domain=").append(domain).append(";");
        }

        if (isNotBlank(path)) {
            buffer.append("path=").append(path).append(";");
        }

        if (isSecure) {
            buffer.append("secure;");
        }

        if (isHttpOnly) {
            buffer.append("HTTPOnly;");
        }

        response.addHeader("Set-Cookie", buffer.toString());
    }

    public static void logOut(HttpServletResponse response, String cookieDomain, String... keys) {
        ReValidates.isTrue(nonNull(response), "response is null");
        ReValidates.isTrue(isNotBlank(cookieDomain), "cookieDomain is null");
        ReValidates.isTrue(nonNull(keys), "keys is empty");

        Arrays.stream(keys).forEach(key -> response.addCookie(deleteCookie(cookieDomain, key)));
    }

}
