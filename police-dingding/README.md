[TOC]

# 钉钉消息类型及数据格式

## text类型

### 数据格式

```json
{
    "msgtype": "text", 
    "text": {
        "content": "我就是我, 是不一样的烟火@156xxxx8827"
    }, 
    "at": {
        "atMobiles": [
            "156xxxx8827", 
            "189xxxx8325"
        ], 
        "isAtAll": false
    }
}
```

| **参数**  | **参数类型** | **必须** | **说明**                                    |
| --------- | ------------ | -------- | ------------------------------------------- |
| msgtype   | String       | 是       | 消息类型，此时固定为：text                  |
| content   | String       | 是       | 消息内容                                    |
| atMobiles | Array        | 否       | 被@人的手机号（在content里添加@人的手机号） |
| isAtAll   | Boolean      | 否       | 是否@所有人                                 |

### 使用方法

```java
// 方式 1
String webhook = "https://oapi.dingtalk.com/robot/send?access_token=xxxx";
String content = "test hha ";
DingDings.sendText(webhook, content);

// 方式 2
String webhook = "https://oapi.dingtalk.com/robot/send?access_token=xxxx";
String content = "test hha ";
List<String> atMobiles = Lists.newArrayList("123xxxxx", "123yyyyyy");
boolean atAll = true;
DingDings.sendText(webhook, content, atMobiles, atAll);

// 方式 3
String webhook = "https://oapi.dingtalk.com/robot/send?access_token=xxxx";
String content = "test hha ";
List<String> atMobiles = Lists.newArrayList("123xxxxx", "123yyyyyy");
boolean atAll = true;

DingTextRequest request = DingTextRequest.of()
                    .content(content)
                    .atMobiles(atMobiles)
                    .atAll(atAll);

DingDings.sendText(webhook, request);
```


## link类型

### 数据格式

```json
{
    "msgtype": "link", 
    "link": {
        "text": "这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林", 
        "title": "时代的火车向前开", 
        "picUrl": "", 
        "messageUrl": "https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI"
    }
}
```

| **参数**   | **参数类型** | **必须** | **说明**                       |
| ---------- | ------------ | -------- | ------------------------------ |
| msgtype    | String       | 是       | 消息类型，此时固定为：link     |
| title      | String       | 是       | 消息标题                       |
| text       | String       | 是       | 消息内容。如果太长只会部分展示 |
| messageUrl | String       | 是       | 点击消息跳转的URL              |
| picUrl     | String       | 否       | 图片URL                        |

### 使用方法
```java
String webhook = "https://oapi.dingtalk.com/robot/send?access_token=xxxx";

DingLinkRequest request = DingLinkRequest.of()
        .title("时代的火车向前开")
        .text("test 这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林")
        .picUrl("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png")
        .messageUrl("https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI");

DingDings.sendLink(webhook, request);
```

## markdown类型

### 数据格式

```json
{
     "msgtype": "markdown",
     "markdown": {
         "title":"杭州天气",
         "text": "#### 杭州天气 @150XXXXXXXX \n> 9度，西北风1级，空气良89，相对温度73%\n> ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)\n> ###### 10点20分发布 [天气](https://www.dingtalk.com) \n"
     },
      "at": {
          "atMobiles": [
              "150XXXXXXXX"
          ],
          "isAtAll": false
      }
 }
```

| **参数**  | **类型** | **必选** | **说明**                                 |
| --------- | -------- | -------- | ---------------------------------------- |
| msgtype   | String   | 是       | 此消息类型为固定markdown                 |
| title     | String   | 是       | 首屏会话透出的展示内容                   |
| text      | String   | 是       | markdown格式的消息                       |
| atMobiles | Array    | 否       | 被@人的手机号（在text内容里要有@手机号） |
| isAtAll   | Boolean  | 否       | 是否@所有人                              |

### 使用方法
```java
String webhook = "https://oapi.dingtalk.com/robot/send?access_token=xxxx";
String title = "test 杭州天气";
String text = "#### 杭州天气 @150XXXXXXXX \\n> 9度，西北风1级，空气良89，相对温度73%\\n> ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)\\n> ###### 10点20分发布 [天气](https://www.dingtalk.com) \\n";

DingDings.sendMarkdown(webhook, title, text);
```

## 整体跳转ActionCard类型

### 数据格式

```json
{
    "actionCard": {
        "title": "乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身", 
        "text": "![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png) ### 乔布斯 20 年前想打造的苹果咖啡厅 Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划", 
        "btnOrientation": "0", 
        "singleTitle" : "阅读全文",
        "singleURL" : "https://www.dingtalk.com/"
    }, 
    "msgtype": "actionCard"
}
```

| **参数**       | **类型** | **必须** | **说明**                                        |
| -------------- | -------- | -------- | ----------------------------------------------- |
| msgtype        | String   | 是       | 此消息类型为固定actionCard                      |
| title          | String   | 是       | 首屏会话透出的展示内容                          |
| text           | String   | 是       | markdown格式的消息                              |
| singleTitle    | String   | 是       | 单个按钮的标题。(设置此项和singleURL后btns无效) |
| singleURL      | String   | 是       | 点击singleTitle按钮触发的URL                    |
| btnOrientation | String   | 否       | 0-按钮竖直排列，1-按钮横向排列                  |

### 使用方法
```java
String webhook = "https://oapi.dingtalk.com/robot/send?access_token=xxxx";
DingSingleActionCardRequest request = DingSingleActionCardRequest.of()
                .title("乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身")
                .text("test ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png) ### 乔布斯 20 年前想打造的苹果咖啡厅 Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划")
                .btnOrientation("0")
                .singleTitle("阅读全文")
                .singleURL("https://www.dingtalk.com/");

DingDings.sendSingleActionCard(webhook, request);
```

## 独立跳转ActionCard类型

### 数据格式

```json
{
    "actionCard": {
        "title": "乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身", 
        "text": "![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png) ### 乔布斯 20 年前想打造的苹果咖啡厅 Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划", 
        "btnOrientation": "0", 
        "btns": [
            {
                "title": "内容不错", 
                "actionURL": "https://www.dingtalk.com/"
            }, 
            {
                "title": "不感兴趣", 
                "actionURL": "https://www.dingtalk.com/"
            }
        ]
    }, 
    "msgtype": "actionCard"
}
```


| **参数**       | **类型** | **必须** | **说明**                       |
| -------------- | -------- | -------- | ------------------------------ |
| msgtype        | String   | 是       | 此消息类型为固定actionCard     |
| title          | String   | 是       | 首屏会话透出的展示内容         |
| text           | String   | 是       | markdown格式的消息             |
| btns           | Array    | 是       | 按钮                           |
| title          | String   | 是       | 按钮标题                       |
| actionURL      | String   | 是       | 点击按钮触发的URL              |
| btnOrientation | String   | 否       | 0-按钮竖直排列，1-按钮横向排列 |

### 使用方法
```java
String webhook = "https://oapi.dingtalk.com/robot/send?access_token=xxxx";

DingMultiActionCardRequest request = DingMultiActionCardRequest.of()
                .title("乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身")
                .text("test ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png) ### 乔布斯 20 年前想打造的苹果咖啡厅 Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划")
                .btnOrientation("0")
                .btn(Btn.of().title("内容不错").actionURL("https://www.dingtalk.com/"))
                .btn(Btn.of().title("不感兴趣").actionURL("https://www.dingtalk.com/"));

DingDings.sendMultiActionCard(webhook, request);
```

## FeedCard类型

### 数据格式

```json
{
    "feedCard": {
        "links": [
            {
                "title": "时代的火车向前开", 
                "messageURL": "https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI", 
                "picURL": "https://gw.alicdn.com/tfs/TB1ayl9mpYqK1RjSZLeXXbXppXa-170-62.png"
            },
            {
                "title": "时代的火车向前开2", 
                "messageURL": "https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI", 
                "picURL": "https://gw.alicdn.com/tfs/TB1ayl9mpYqK1RjSZLeXXbXppXa-170-62.png"
            }
        ]
    }, 
    "msgtype": "feedCard"
}
```

| **参数**   | **类型** | **必须** | **说明**                 |
| ---------- | -------- | -------- | ------------------------ |
| msgtype    | String   | 是       | 此消息类型为固定feedCard |
| title      | String   | 是       | 单条信息文本             |
| messageURL | String   | 是       | 点击单条信息到跳转链接   |
| picURL     | String   | 是       | 单条信息后面图片的URL    |

### 使用方法
```java
String webhook = "https://oapi.dingtalk.com/robot/send?access_token=xxxx";

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
```


# 参考

[钉钉机器人官方文档](https://ding-doc.dingtalk.com/doc#/serverapi2/qf2nxq)
