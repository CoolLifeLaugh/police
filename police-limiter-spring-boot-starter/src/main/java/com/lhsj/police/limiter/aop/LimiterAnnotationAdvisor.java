package com.lhsj.police.limiter.aop;

import com.lhsj.police.limiter.annotation.Throttle;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.NonNull;

import static java.util.Objects.isNull;

public class LimiterAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private Advice   advice;
    private Pointcut pointcut;

    public LimiterAnnotationAdvisor(LimiterAnnotationInterceptor advice) {
        this.advice = advice;
    }

    @Override
    @NonNull
    public Pointcut getPointcut() {
        if (isNull(pointcut)) {
            Pointcut cpc = new AnnotationMatchingPointcut(Throttle.class, true);
            Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(Throttle.class);
            pointcut = new ComposablePointcut(cpc).union(mpc);
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
