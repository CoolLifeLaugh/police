# 基于注解的统一controller的response的AOP

两个返回时机，正常请求返回，抛异常返回，组件都支持。controller的返回值类型，只需要定义成自己关心的类型。

正常返回的数据格式：
```json
{
    "success":true,
    "code":"200",
    "msg":"request success",
    "data":true // 这里，controller的返回值类型是boolean
}
```

抛异常返回的数据格式：
```json
{
    "success":false,
    "code":"500",
    "msg":"具体的异常信息",
    "data":null
}
```

## 快速开始

### 引入pom

```xml
<dependency>
    <groupId>com.lhsj</groupId>
    <artifactId>police-responses-spring-boot-starter</artifactId>
    <version>xxxx</version>
</dependency>
```

### 配置properties

表示启用此组件

```properties
# responses
police.responses.enable=true
```

### 使用

在方法上，写上注解即可。

```java
@Responses()
@ResponseBody
@RequestMapping(value = "/simple", method = RequestMethod.GET)
public boolean simple() {
    return responsesService.simple();
}
```
