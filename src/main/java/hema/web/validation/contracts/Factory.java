package hema.web.validation.contracts;

import hema.web.validation.concerns.haystack.Haystack;

import java.util.Map;

public interface Factory {

    /**
     * Create a new Validator instance.
     *
     * @param data         Input data.
     * @param validateRule Rule proxy.
     * @param messages     Custom rule validate Message sources.
     * @param attributes   Custom rule attribute name.
     * @return Return new Validator instance.
     */
    Validator make(Map<String, Object> data, ValidateRule validateRule, Haystack<Object> messages, Haystack<String> attributes);

    void extend(String rule, CustomValidateRulePredicate closure, String message);

    @FunctionalInterface
    interface CustomValidateRulePredicate {
        boolean validate(String attribute, Object value, Map<String, Object> parameters);
    }

}
