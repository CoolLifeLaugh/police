package com.lhsj.police.dingding.base;

import com.lhsj.police.core.log.ReLogs;
import com.lhsj.police.core.net.RestClient;
import com.lhsj.police.dingding.request.AbstractDingRequest;
import com.lhsj.police.dingding.request.DingFeedCardRequest;
import com.lhsj.police.dingding.request.DingLinkRequest;
import com.lhsj.police.dingding.request.DingMarkdownRequest;
import com.lhsj.police.dingding.request.DingMultiActionCardRequest;
import com.lhsj.police.dingding.request.DingSingleActionCardRequest;
import com.lhsj.police.dingding.request.DingTextRequest;

import java.util.List;

import static com.lhsj.police.core.net.RestClient.IGNORE_CALLBACK;

public final class DingDings {

    private static void send(String webhook, AbstractDingRequest request) {
        try {
            RestClient.instance().postAsync(webhook, request, IGNORE_CALLBACK);
        } catch (Throwable e) {
            ReLogs.warn(DingDings.class.getName(), e);
        }
    }

    /**
     * 异步推送钉钉TEXT消息
     *
     * @param webhook 钉钉机器人URL
     * @param request 请求对象
     */
    public static void sendText(String webhook, DingTextRequest request) {
        send(webhook, request);
    }

    /**
     * 异步推送钉钉TEXT消息
     *
     * @param webhook   钉钉机器人URL
     * @param content   推送的信息文本
     * @param atMobiles 钉钉群某些人的电话号码
     * @param atAll     是否需要@这些电话号码，一般传true就可以了
     */
    public static void sendText(String webhook, String content, List<String> atMobiles, boolean atAll) {
        try {
            DingTextRequest request = DingTextRequest.of()
                    .content(content)
                    .atMobiles(atMobiles)
                    .atAll(atAll);

            sendText(webhook, request);
        } catch (Throwable e) {
            ReLogs.warn(DingDings.class.getName(), e);
        }
    }

    /**
     * 异步推送钉钉TEXT消息
     *
     * @param webhook 钉钉机器人URL
     * @param content 推送的信息文本
     */
    public static void sendText(String webhook, String content) {
        sendText(webhook, content, null, false);
    }

    /**
     * 异步推送钉钉LINK消息
     *
     * @param webhook 钉钉机器人URL
     * @param request 请求对象
     */
    public static void sendLink(String webhook, DingLinkRequest request) {
        send(webhook, request);
    }

    /**
     * 异步推送钉钉LINK消息
     *
     * @param webhook    钉钉机器人URL
     * @param title      标题
     * @param text       文本内容
     * @param pickUrl    图标地址
     * @param messageUrl 跳转的连接
     */
    public static void sendLink(String webhook, String title, String text, String pickUrl, String messageUrl) {
        try {
            DingLinkRequest request = DingLinkRequest.of()
                    .title(title)
                    .text(text)
                    .picUrl(pickUrl)
                    .messageUrl(messageUrl);

            RestClient.instance().postAsync(webhook, request, IGNORE_CALLBACK);
        } catch (Throwable e) {
            ReLogs.warn(DingDings.class.getName(), e);
        }
    }

    /**
     * 异步推送钉钉MARKDOWN消息
     *
     * @param webhook 钉钉机器人URL
     * @param request 请求对象
     */
    public static void sendMarkdown(String webhook, DingMarkdownRequest request) {
        send(webhook, request);
    }

    /**
     * 异步推送钉钉MARKDOWN消息
     *
     * @param webhook 钉钉机器人URL
     * @param title   标题
     * @param text    文本内容
     */
    public static void sendMarkdown(String webhook, String title, String text) {
        try {
            DingMarkdownRequest request = DingMarkdownRequest.of()
                    .title(title)
                    .text(text);

            RestClient.instance().postAsync(webhook, request, IGNORE_CALLBACK);
        } catch (Throwable e) {
            ReLogs.warn(DingDings.class.getName(), e);
        }
    }

    /**
     * 异步推送钉钉MARKDOWN消息
     *
     * @param webhook   钉钉机器人URL
     * @param title     标题
     * @param text      文本内容
     * @param atMobiles 钉钉群某些人的电话号码
     * @param atAll     是否需要@这些电话号码，一般传true就可以了
     */
    public static void sendMarkdown(String webhook, String title, String text, List<String> atMobiles, boolean atAll) {
        try {
            DingMarkdownRequest request = DingMarkdownRequest.of()
                    .title(title)
                    .text(text)
                    .atMobiles(atMobiles)
                    .atAll(atAll);

            RestClient.instance().postAsync(webhook, request, IGNORE_CALLBACK);
        } catch (Throwable e) {
            ReLogs.warn(DingDings.class.getName(), e);
        }
    }

    /**
     * 异步推送钉钉整体跳转消息
     *
     * @param webhook 钉钉机器人URL
     * @param request 请求对象
     */
    public static void sendSingleActionCard(String webhook, DingSingleActionCardRequest request) {
        send(webhook, request);
    }

    /**
     * 异步推送钉钉独立跳转消息
     *
     * @param webhook 钉钉机器人URL
     * @param request 请求对象
     */
    public static void sendMultiActionCard(String webhook, DingMultiActionCardRequest request) {
        send(webhook, request);
    }

    /**
     * 异步推送钉钉FeedCard消息
     *
     * @param webhook 钉钉机器人URL
     * @param request 请求对象
     */
    public static void sendFeedCard(String webhook, DingFeedCardRequest request) {
        send(webhook, request);
    }
}
