package hema.web.validation.concerns;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class ValidationRuleParser {

    private Map<String, Object> data;

    private Set<String> implicitAttributes = new HashSet<>();

    ValidationRuleParser(Map<String, Object> data) {
        this.data = data;
    }

    Map<String, Object> explode(Map<String, Object[]> rules) {

        implicitAttributes.clear();

        Map<String, Object> result = new HashMap<>();
        result.put("rules", explodeRules(rules));
        result.put("implicitAttributes", implicitAttributes);

        return result;
    }

    private Map<String, Object[]> explodeRules(Map<String, Object[]> rules) {

        rules.forEach((key, rule) -> {

            if (key.contains("*")) {

                rules.remove(key, rule);
            } else {
                rules.put(key, rule);
            }

        });

        return rules;
    }

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
