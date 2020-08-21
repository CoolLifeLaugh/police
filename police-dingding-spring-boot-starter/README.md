# 钉钉消息切面

方法抛出异常时，给指定的钉钉机器人，发出钉钉消息。

## 消息格式
```
keyword: test
date: 2020-08-14 14:17:29
ip: 30.117.24.239
traceId: f8adbeb4-f970-14ae-063c-7610b2f281f2
java.lang.RuntimeException: 
  at com.lhsj.police.dingding.service.DingDingService.sendText(DingDingService.java:16)
  at com.lhsj.police.dingding.service.DingDingService$$FastClassBySpringCGLIB$$720351b0.invoke(<generated>)
  at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
  at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771)
  at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
```

## 快速开始

### 引入pom

```xml
<dependency>
    <groupId>com.lhsj</groupId>
    <artifactId>police-dingding-spring-boot-starter</artifactId>
    <version>xxxx</version>
</dependency>
```

### 配置properties

表示启用此组件

```properties
# dingding
police.dingding.enable=true
```

### 使用

在方法上，写上注解即可。

```java
// 声明全局常量
public static final String webhook = "https://oapi.dingtalk.com/robot/send?access_token=1d2f25663406d1a65729ab2ae979469b96d4b15a2454034d42c6223d03dd0bc9";
public static final String keyword = "test";
public static final String sign    = "SEC170bb4ac78971fba6d23f00e9f336f0f8ace8a1e88145782eff1e66f9d070d24";

@DingDing(webhook = webhook, keyword = keyword, sign = sign)
public boolean sendText() {
    doSomething();
    return true;
}
```

> keyword和sign不是必须的，得看你的钉钉机器人，有没有开启【关键词】或【签名】，如果有，就需要加上相应的注解。


