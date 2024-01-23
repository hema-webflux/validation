package hema.web.validation.support;

import org.springframework.lang.NonNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

final class ValidateRuleProxy implements InvocationHandler {

    private final Map<String, Set<String>> defaultRules = new HashMap<>();

    private final Map<String, Set<String>> rules = new HashMap<>();

    private String currentField = null;

    private final String[] implicitRules = {
            "required",
            "requiredUnless",
            "requiredWith",
            "requiredWithout",
            "accepted",
            "acceptedIf",
            };

    private final String[] normalRules = {
            "nullable", "string", "bool", "json",
            "url", "uppercase", "lowercase", "email", "phone", "map",
            "distinct", "date", "identityNumber", "bankCardNumber", "array"
    };

    private final String[] authRules = {"email", "phone", "identityNumber", "bankCardNumber"};

    private final static String[] singleParameterRules = {
            "regex",
            "different", "isEnum"
    };

    private final String[] dependentRules = {
            "same",
            "before",
            "after",
            "confirmed",
            "dateEquals",
            "unique",
            "exists"
    };

    private final static String[] sizeRules = {"size", "between", "min", "max",};

    private final static String[] numericRules = {"digits", "digitsBetween", "decimal", "integer", "numeric",};

    private final static String[] extendParametersRules = {
            "in", "startWith", "endWith", "doesntStartWith", "doesntEndWith"
    };

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (isCallBoundaryMethod(method)) {
            return rules;
        }

        if (isSetField(method)) {
            currentField = (String) args[0];
        }

        return proxy;
    }

    private boolean isCallBoundaryMethod(Method method) {
        return method.getName().equals("rules");
    }

    private boolean isSetField(Method method) {
        return method.getName().equals("field");
    }

    private boolean hasRuleMethod(String group, Method method) {
        return defaultRules.get(group).contains(method.getName());
    }

    private void store(String value) {

        if (!rules.containsKey(currentField)) {

            Set<String> validateRules = new HashSet<>();

            validateRules.add(value);

            rules.put(currentField, validateRules);
        } else {
            rules.get(currentField).add(value);
        }
    }

    interface Store {
        Store push(String key, @NonNull Object value);
    }
}
