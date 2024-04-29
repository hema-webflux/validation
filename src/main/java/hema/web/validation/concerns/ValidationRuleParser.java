package hema.web.validation.concerns;

class ValidationRuleParser {
    protected static String normalizeRule(String rule) {
        return switch (rule) {
            case "Int" -> "Integer";
            case "Bool" -> "Boolean";
            default -> rule;
        };
    }
}
