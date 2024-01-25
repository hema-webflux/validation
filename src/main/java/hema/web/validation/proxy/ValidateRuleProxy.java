package hema.web.validation.proxy;

import hema.web.validation.contracts.ValidateRule;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

final public class ValidateRuleProxy implements InvocationHandler {

    private final Map<String, Set<ValidateRule.Access>> rules = new HashMap<>();

    private String currentField = null;

    private final ValidateRule.Access access;

    ValidateRuleProxy(ValidateRule.Access access) {
        this.access = access;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        if (isGetValuesAction(method)) {
            return rules;
        }

        if (isSetAttributeAction(method)) {
            currentField = (String) args[0];
            return proxy;
        }

        makeStore().push(prepareRules(args, method));

        return proxy;
    }

    private boolean isGetValuesAction(Method method) {
        return method.getName().equals("rules");
    }

    private boolean isSetAttributeAction(Method method) {
        return method.getName().equals("field");
    }

    private Object[] prepareRules(Object[] args, Method method) {

        if (args == null) {
            return new Object[]{method.getName()};
        }

        int parameterLength = args.length;

        Object[] rules = new Object[parameterLength + 1];

        rules[0] = method.getName();

        for (int i = 1; i <= rules.length; i++) {
            if (parameterLength >= i) {
                rules[i] = args[i - 1];
            }
        }

        return rules;
    }

    private Store makeStore() {
        if (rules.containsKey(currentField)) {
            return rule -> rules.get(currentField).add(access.clone().setData(rule));
        }

        return rule -> {
            Set<ValidateRule.Access> newRules = new HashSet<>();

            newRules.add(access.clone().setData(rule));

            rules.put(currentField, newRules);
        };
    }

    private interface Store {
        void push(Object[] rule);
    }
}
