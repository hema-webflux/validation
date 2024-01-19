package hema.web.validation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public final class ValidateRuleProxyInvocationHandler implements InvocationHandler {

    private final String endMethod = "rules";

    private final static String[] normalRules = {
            "required", "nullable", "string", "integer", "numeric", "bool", "json",
            "url", "uppercase", "lowercase", "accepted", "email", "phone", "map",
            "distinct", "date", "bank", "card", "identityNumber", "bankCardNumber", "array"
    };

    private final static String[] singleParameterRules = {
            "size", "same", "digits", "regex", "before", "after", "min", "max",
            "acceptedIf", "confirmed", "dateEquals", "different", "isEnum"
    };

    private final static String[] fixedParametersRules = {"unique", "exists", "digitsBetween", "between", "decimal"};

    private final static String[] extendParametersRules = {
            "in", "requiredUnless", "requiredWith", "requiredWithout", "startWith", "endWith", "doesntStartWith", "doesntEndWith"
    };

    private final Map<String, Set<String>> defaultRules;

    ValidateRuleProxyInvocationHandler() {

        defaultRules = new HashMap<>();

        defaultRules.put("normalRules", new HashSet<>(Arrays.asList(normalRules)));
        defaultRules.put("singleParameterRules", new HashSet<>(Arrays.asList(singleParameterRules)));
        defaultRules.put("fixedParametersRules", new HashSet<>(Arrays.asList(fixedParametersRules)));
        defaultRules.put("extendParametersRules", new HashSet<>(Arrays.asList(extendParametersRules)));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
