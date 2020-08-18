package com.lhsj.police.script.listener;

import com.lhsj.police.core.log.ReLogs;
import com.lhsj.police.core.reflect.ReReflections;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.util.Objects.isNull;

public class ScriptControllerListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final String controllerName           = "policeScriptController";
    private static final String mappingName              = "requestMappingHandlerMapping";
    private static final String getMappingForMethodName  = "getMappingForMethod";
    private static final String detectHandlerMethodsName = "detectHandlerMethods";

    /**
     * 注册controller
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();

        try {
            if (!context.containsBean(controllerName)) {
                ReLogs.warn("ScriptControllerListener policeScriptController null");
                return;
            }

            final RequestMappingHandlerMapping handlerMapping = (RequestMappingHandlerMapping) context.getBean(mappingName);

            Object controller = context.getBean(controllerName);

            unregisterController(handlerMapping, controller);
            registerController(handlerMapping);
        } catch (Throwable e) {
            ReLogs.warn("ScriptControllerListener.registerController error", e);
        }
    }

    private void registerController(RequestMappingHandlerMapping handlerMapping)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 注册Controller
        Method method = handlerMapping.getClass()
                .getSuperclass()
                .getSuperclass()
                .getDeclaredMethod(detectHandlerMethodsName, Object.class);
        method.setAccessible(true);
        method.invoke(handlerMapping, "policeScriptController");
    }

    private void unregisterController(RequestMappingHandlerMapping handlerMapping, Object controller) {
        final Class<?> targetClass = controller.getClass();

        ReReflections.MethodCallback callback = method -> {
            Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
            try {
                Method createMappingMethod = RequestMappingHandlerMapping.class
                        .getDeclaredMethod(getMappingForMethodName, Method.class, Class.class);
                createMappingMethod.setAccessible(true);
                RequestMappingInfo mappingInfo = (RequestMappingInfo) createMappingMethod.invoke(handlerMapping, specificMethod, targetClass);

                if (isNull(mappingInfo)) {
                    return;
                }

                handlerMapping.unregisterMapping(mappingInfo);
            } catch (Exception e) {
                ReLogs.warn("ScriptControllerListener.unregisterController error", e);
            }
        };

        ReReflections.doWithMethods(targetClass, callback, ReReflections.USER_DECLARED_METHODS);
    }
}
