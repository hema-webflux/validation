package hema.web.validation.concerns;

import java.util.Set;

class ValidateRules {
    protected Set<String> implicitRules = Set.of(
            "Accepted",
            "Required",
            "String",
            "Url",
            "Integer",
            "Numeric",
            "Json",
            "Map",
            "Array",
            "Uppercase",
            "Lowercase",
            "Email",
            "Phone",
            "Date",
            "Bool",
            "IdCard",
            "BankCard");

    protected Set<String> dependentRules = Set.of("Same", "Confirmed", "After", "Before");

    protected final String[] sizeRules = {"max", "min", "between"};

    protected final String[] numericRule = {"Integer", "Numeric"};

    protected final String[] excludeRules = {"Exclude", "ExcludeIf", "ExcludeUnless", "ExcludeWith", "ExcludeWithout"};

    final protected boolean isImplicit(String rule) {
        return implicitRules.contains(rule);
    }

    final protected boolean hasRule(String needle, String[] haystack) {

        for (String rule : haystack) {
            if (rule.equals(needle)) {
                return true;
            }
        }

        return false;
    }
}
