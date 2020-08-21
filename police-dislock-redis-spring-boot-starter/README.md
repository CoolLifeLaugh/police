# 基于注解的分布式锁的AOP

分布式锁，使用的是Spring的官方实现，基本满足生产需要。这里对分布式锁的易用性，做了进一步优化。只需要在方法上，声明一个注解，就可以实现分布式锁了。

## 快速开始

### 引入pom

```xml
<dependency>
    <groupId>com.lhsj</groupId>
    <artifactId>police-dislock-redis-spring-boot-starter</artifactId>
    <version>xxxx</version>
</dependency>
```

### 配置properties

表示启用此组件

```properties
# dislock
police.dislock.redis.enable=true
## 分布式锁组件，存储在redis的数据的失效时间，单位毫秒，默认是60000，即60s
police.dislock.redis.expire-after=10000
```

### 使用

在方法上，写上注解即可。

```java
@Dislock(key = "'key_'.concat(#key)")
public boolean simple(String key) {
    doSomething();
    return true;
}
```

key:使用的是Spring的SPEL表达式，用法和spring cache的@Cacheable.key一样，[链接](https://docs.spring.io/spring/docs/4.3.10.RELEASE/spring-framework-reference/html/cache.html#cache-annotations-cacheable-key)
