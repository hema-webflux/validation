package hema.web.validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractValidateRuleProxyInvocationHandler {

    private static final String[] rules = {
            "required", "nullable", "unique", "same", "integer", "numeric",
            "email", "phone", "min", "max", "confirmed", "json", "map",
            "inMap", "bool", "date", "in", "before", "after", "between",
            "decimal", "assertEnum", "requiredIf", "exists", "regex",
            "accepted", "acceptedIf", "dateEquals", "different", "digits",
            "digitsBetween", "distinct", "distinctStrict", "distinctIgnore_case",
            "startWith", "endWith", "doesntStartWith", "doesntEndWith",
            "lowercase", "mimes", "missing", "missingIf", "requiredUnless",
            "requiredWith", "requiredWithAll", "requiredWithout",
            "requireMapKeys", "url", "uppercase", "size", "array", "inArray",
            };

    private static final Set<String> ruleWrapper = new HashSet<>(Arrays.asList(rules));

    final protected boolean hasRule(String rule) {
        return ruleWrapper.contains(rule);
    }
}
