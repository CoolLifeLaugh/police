package com.lhsj.police.mock.aop;

import com.lhsj.police.mock.configuration.MockProperties;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.NonNull;

import static java.util.Objects.isNull;

public class MockAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private Advice         advice;
    private Pointcut       pointcut;
    private MockProperties mockProperties;

    public MockAnnotationAdvisor(MockAnnotationInterceptor advice, MockProperties mockProperties) {
        this.advice = advice;
        this.mockProperties = mockProperties;
    }

    @Override
    @NonNull
    public Pointcut getPointcut() {
        if (isNull(pointcut)) {
            pointcut = new AspectJExpressionPointcut();
            ((AspectJExpressionPointcut) pointcut).setExpression(mockProperties.getExpression());
        }
        return pointcut;
    }

    @Override
    @NonNull
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }
}
