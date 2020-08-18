package com.lhsj.police.web.request;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static java.util.Optional.ofNullable;

public class ReRequests {

    public static String getParameter(HttpServletRequest req, String name) {
        return getParameter(req, name, null);
    }

    public static String getParameter(HttpServletRequest req, String name, String defaultValue) {
        return ofNullable(req)
                .filter(e -> StringUtils.isNotBlank(name))
                .map(e -> e.getParameter(name))
                .orElse(defaultValue);
    }

}
