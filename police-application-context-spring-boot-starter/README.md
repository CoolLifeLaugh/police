#  ApplicationContext辅助组件

静态暴露spring的ApplicationContext对象。

## 快速开始

### 引入pom

```xml
<dependency>
    <groupId>com.lhsj</groupId>
    <artifactId>police-application-context-spring-boot-starter</artifactId>
    <version>xxxx</version>
</dependency>
```

### 配置properties

表示启用此组件

```properties
# context
police.application.context.enable=true
```

## 使用方法

```java
String beanName = "contextController";

// 用法一
ContextController controller =  ApplicationContextHolder.getContext().getBean(beanName);

// 用法二
ContextController controller =  ApplicationContextHolder.getBean(beanName);
```