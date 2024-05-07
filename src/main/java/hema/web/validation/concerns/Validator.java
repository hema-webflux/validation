package hema.web.validation.concerns;

import hema.web.validation.exception.ValidationException;

import java.util.Map;

public interface Validator {

    Map<String, Object> validated() throws ValidationException;

    Map<String, String[]> rules();

    boolean fails();

    String[] field();

    MessageBag errors();

    void after(ValidateHookConsumer consumer);

    /**
     * Instruct the validator to stop validating after the first rule failure.
     *
     * @param stopOnFirstFailure boolean
     * @return Validator instance.
     */
    Validator stopOnFirstFailure(boolean stopOnFirstFailure);

    @FunctionalInterface
    interface ValidateRulePredicate {
        boolean validate(String attribute, Object value, Map<String, Object> parameters);
    }

    @FunctionalInterface
    interface ValidateHookConsumer {
        void accept(Validator validator);
    }
}
