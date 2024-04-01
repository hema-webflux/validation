package hema.web.validation.concerns;

class ValidateRules {
    protected final String[] implicitRules = {
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
            "BankCard"
    };

    protected final String[] dependentRules = {
            "Same",
            "Confirmed",
            "After",
            "Before"
    };

    protected final String[] sizeRules = {
            "max",
            "min",
            "between"
    };

    protected final String[] numericRule = {
            "Integer",
            "Numeric"
    };

    final protected boolean dependsOnOtherFields(String rule) {
        return hasRule(rule, dependentRules);
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
