# 基于注解的MOCK数据的AOP

对目标方法的返回值，mock数据返回。

## 快速开始

### 引入pom

```xml
<dependency>
    <groupId>com.lhsj</groupId>
    <artifactId>police-mock-spring-boot-starter</artifactId>
    <version>xxxx</version>
</dependency>
```

### 配置properties

表示启用此组件

```properties
# mock
police.mock.enable=true
```

### 使用

在方法上，写上注解即可。

#### 本地mock

mock的数据，存放在本地。

```java
@Mock(key = "'simple'", path = "'/tmp/mock'")
public boolean simple() {
    return true;
}
```

配置mock数据的文件，路径为`/tmp/mock`，内容如下：

```
simple=false
```

#### 远程mock

mock的数据，请求远程的接口获取。

```java
@Mock(key = "'remote'", remote = true, url = "http://127.0.0.1:8888/api/mock")
public boolean remote() {
    return true;
}
```

### 注意事项

mock的数据，存储的方式是键值对，key的使用方式是SPEL表达式，value是json数据，所以组件会对数据做json反序列化。
path，也使用SPEL表达式，比较灵活一些。