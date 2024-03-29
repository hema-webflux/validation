package hema.web.validation.concerns;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

final class ValidateRuleProxy implements InvocationHandler {

    private final Map<String, Object[]> rules;

    private String currentField = null;

    ValidateRuleProxy(Map<String, Object[]> rules) {
        this.rules = rules;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        if (method.getName().equals("rules")) {
            return rules;
        }

        if (method.getName().equals("field")) {
            currentField = (String) args[0];
            return proxy;
        }

        if (Objects.isNull(args)) {
            rules.put(currentField, new Object[]{method.getName()});
            return proxy;
        }

        return proxy;
    }
}
