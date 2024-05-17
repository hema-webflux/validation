package hema.web.validation.concerns;

import hema.web.validation.message.Str;

import java.util.*;

final class ValidationRuleParser {

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

    public static Object[] parse(Object rule) {

        if (rule instanceof Validator.ValidateRulePredicate) {
            return new Object[]{rule, null};
        }

        Object[] parameters = new Object[2];

        if (rule instanceof String) {
            parameters = parseStringRule(rule.toString());
        }

        if (rule instanceof Object[]) {
            parameters = parseArrayRule((Object[]) rule);
        }

        parameters[0] = normalizeRule((String) parameters[0]);

        return parameters;
    }

    private static Object[] parseArrayRule(Object[] rule) {
        return new Object[]{Str.studly(rule[0].toString().trim()), Arrays.copyOfRange(rule, 1, rule.length)};
    }


    private static Object[] parseStringRule(String rule) {

        String[] parameters = null;

        if (rule.contains(":")) {

            String[] parts = rule.split(":", 2);

            rule = parts[0];

            parameters = parseParameters(rule, parts[1]);
        }

        return new Object[]{Str.studly(rule.trim()), parameters};
    }

    private static String[] parseParameters(String rule, String parameters) {
        return ruleIsRegex(rule) ? new String[]{parameters} : parameters.split(",");
    }

    private static boolean ruleIsRegex(String rule) {

        String[] rules = {"regex", "notRegex", "not_regex", "notregex"};

        for (String regexRule : rules) {
            if (regexRule.equalsIgnoreCase(rule)) {
                return true;
            }
        }

        return false;
    }

    private static String normalizeRule(String rule) {
        return switch (rule) {
            case "Int" -> "Integer";
            case "Bool" -> "Boolean";
            default -> rule;
        };
    }
}
