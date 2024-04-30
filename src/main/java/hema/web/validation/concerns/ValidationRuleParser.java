package hema.web.validation.concerns;

import java.util.Map;

class ValidationRuleParser {

    protected static boolean ruleIsRegex(String rule) {

        String[] rules = {"regex", "notRegex", "notregex"};

        for (String ruleName : rules) {
            if (ruleName.equalsIgnoreCase(rule)) {
                return true;
            }
        }

        return false;
    }

    protected static String normalizeRule(String rule) {
        return switch (rule) {
            case "Int" -> "Integer";
            case "Bool" -> "Boolean";
            default -> rule;
        };
    }
}
