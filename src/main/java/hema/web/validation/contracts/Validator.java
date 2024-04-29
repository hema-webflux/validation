package hema.web.validation.contracts;

import hema.web.validation.exception.ValidationException;

import java.util.Map;

public interface Validator {

    Map<String, Object> validated() throws ValidationException;

    Map<String, String[]> rules();

    boolean fails();

    String[] field();

    MessageBag errors();

    void after(ValidateHookConsumer consumer);

    @FunctionalInterface
    interface ValidateRulePredicate {
        boolean validate(String attribute, Object value, Map<String, Object> parameters);
    }

    @FunctionalInterface
    interface ValidateHookConsumer {
        void accept(Validator validator);
    }
}
