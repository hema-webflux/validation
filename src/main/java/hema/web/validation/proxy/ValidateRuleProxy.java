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

        int count = args.length;

        Object[] rules = new Object[count + 1];

        rules[0] = method.getName();

        for (int i = 1; i <= rules.length; i++) {
            if (count >= i) {
                rules[i] = args[i - 1];
            }
        }

        return rules;
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
