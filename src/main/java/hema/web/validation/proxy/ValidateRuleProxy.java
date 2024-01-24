package hema.web.validation.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

final public class ValidateRuleProxy implements InvocationHandler {

    private final Map<String, Set<Object[]>> rules = new HashMap<>();

    private String currentField = null;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        if (isGetValuesAction(method)) {
            return rules;
        }

        if (isSetAttributeAction(method)) {
            currentField = (String) args[0];
        }

        makeStore().push(initialRuleArray(args, method));

        return proxy;
    }

    public boolean isGetValuesAction(Method method) {
        return method.getName().equals("rules");
    }

    public boolean isSetAttributeAction(Method method) {
        return method.getName().equals("field");
    }

    private Object[] initialRuleArray(Object[] args, Method method) {

        if (args.length < 1) {
            return new Object[]{method.getName()};
        }

        Object[] rule = new Object[args.length + 1];

        rule[0] = method.getName();

        for (int i = 1; i <= rule.length; i++) {
            rule[i] = args[i - 1];
        }

        return rule;
    }

    private Store makeStore() {
        if (rules.containsKey(currentField)) {
            return rule -> rules.get(currentField).add(rule);
        }

        return rule -> {
            Set<Object[]> newRules = new HashSet<>();

            newRules.add(rule);

            rules.put(currentField, newRules);
        };
    }

    private interface Store {
        void push(Object[] rule);
    }
}
