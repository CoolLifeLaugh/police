从spring-cache提炼出来的实现，主要用来解析表达式。

使用方法

```java
private String createKey(MethodInvocation invocation, String key) {
    Method method = invocation.getMethod();
    Object[] args = invocation.getArguments();
    Object target = invocation.getThis();
    Class<?> targetClass = invocation.getThis().getClass();

    EvaluationContext context = evaluator.createEvaluationContext(Lists.newArrayList(), method, args, target, targetClass, beanFactory);

    AnnotatedElementKey methodCacheKey = new AnnotatedElementKey(method, targetClass);

    return (String) evaluator.key(key, methodCacheKey, context);
}
```

