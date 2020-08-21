# 基于注解的异常处理的AOP

捕捉异常的AOP组件。

## 快速开始

### 引入pom

```xml
<dependency>
    <groupId>com.lhsj</groupId>
    <artifactId>police-exceptions-spring-boot-starter</artifactId>
    <version>xxxx</version>
</dependency>
```

### 配置properties

表示启用此组件

```properties
# exceptions
police.exceptions.enable=true
```

### 使用

在方法上，写上注解即可。

```java
@ExceptionResolver
public boolean simple(String key) {
    doSomething();
    return true;
}
```
