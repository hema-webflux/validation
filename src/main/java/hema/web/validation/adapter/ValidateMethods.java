package hema.web.validation.adapter;

final public class ValidateMethods {
    private final String[] implicitRuleMethods = {"required", "requiredUnless", "requiredWith", "requiredWithout", "accepted", "acceptedIf"};

    private final String[] wordRuleMethods = {"uppercase", "lowercase", "startWith", "endWith", "doesntStartWith", "doesntEndWith"};

    private final String[] typeRuleMethods = {"string", "bool", "json", "url", "date"};

    private final String[] authRuleMethod = {"email", "phone", "identityNumber", "bankCardNumber"};

    private final String[] regexRuleMethods = {"regex", "notRegex"};

    private final String[] elementRuleMethods = {"in", "inArray", "inMap", "distinct", "map", "array",};

    private final String[] classRuleMethods = {"isEnum"};

    private final String[] dependentRuleMethods = {"same", "before", "after", "confirmed", "dateEquals", "unique", "exists", "different"};

    private final String[] sizeRuleMethods = {"size", "between", "min", "max",};

    private final String[] numericRuleMethods = {"digits", "digitsBetween", "decimal", "integer", "numeric",};

}
