package com.lhsj.police.dingding;

import com.lhsj.police.dingding.base.DingDings;
import com.lhsj.police.dingding.request.Btn;
import com.lhsj.police.dingding.request.DingFeedCardRequest;
import com.lhsj.police.dingding.request.DingLinkRequest;
import com.lhsj.police.dingding.request.DingMultiActionCardRequest;
import com.lhsj.police.dingding.request.DingSingleActionCardRequest;
import com.lhsj.police.dingding.request.FeedLink;

public class DingDingApp {

    private static String webhook = "https://oapi.dingtalk.com/robot/send?access_token=1d2f25663406d1a65729ab2ae979469b96d4b15a2454034d42c6223d03dd0bc9";

    public static void main(String[] args) {
        testFeedCard();
    }

    private static void testText() {
        String content = "test hha ";
        DingDings.sendText(webhook, content);
    }

    private static void testLink() {
        DingLinkRequest request = DingLinkRequest.of()
                .title("时代的火车向前开")
                .text("test 这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林")
                .picUrl("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png")
                .messageUrl("https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI");
        DingDings.sendLink(webhook, request);
    }

    private static void testMarkdown() {
        String title = "test 杭州天气";
        String text = "#### 杭州天气 @150XXXXXXXX \\n> 9度，西北风1级，空气良89，相对温度73%\\n> ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)\\n> ###### 10点20分发布 [天气](https://www.dingtalk.com) \\n";
        DingDings.sendMarkdown(webhook, title, text);
    }

    private static void testSingleActionCard() {
        DingSingleActionCardRequest request = DingSingleActionCardRequest.of()
                .title("乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身")
                .text("test ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png) ### 乔布斯 20 年前想打造的苹果咖啡厅 Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划")
                .btnOrientation("0")
                .singleTitle("阅读全文")
                .singleURL("https://www.dingtalk.com/");

        DingDings.sendSingleActionCard(webhook, request);
    }

    private static void testMultiActionCard() {
        DingMultiActionCardRequest request = DingMultiActionCardRequest.of()
                .title("乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身")
                .text("test ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png) ### 乔布斯 20 年前想打造的苹果咖啡厅 Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划")
                .btnOrientation("0")
                .btn(Btn.of().title("内容不错").actionURL("https://www.dingtalk.com/"))
                .btn(Btn.of().title("不感兴趣").actionURL("https://www.dingtalk.com/"));

        DingDings.sendMultiActionCard(webhook, request);
    }

    private static void testFeedCard() {
        DingFeedCardRequest request = DingFeedCardRequest.of()
                .link(
                        FeedLink.of()
                                .title("test 时代的火车向前开")
                                .messageURL("https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI")
                                .picURL("https://gw.alicdn.com/tfs/TB1ayl9mpYqK1RjSZLeXXbXppXa-170-62.png")
                )
                .link(
                        FeedLink.of()
                                .title("test 时代的火车向前开2")
                                .messageURL("https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI")
                                .picURL("https://gw.alicdn.com/tfs/TB1ayl9mpYqK1RjSZLeXXbXppXa-170-62.png")
                );

        DingDings.sendFeedCard(webhook, request);
    }
}
