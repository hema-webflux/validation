package hema.web.validation;

import hema.web.validation.contracts.Factory;
import hema.web.validation.contracts.ValidateRule;
import hema.web.validation.contracts.ValidatesWhenResolved;
import hema.web.validation.contracts.Validator;
import hema.web.validation.exception.UnauthorizedException;
import hema.web.validation.exception.ValidationException;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public abstract class FormValidator implements ValidatesWhenResolved {

    private Validator validator = null;

    private final ApplicationContext container;

    public FormValidator(ApplicationContext container) {
        this.container = container;
    }

    protected abstract boolean authorize();

    private void execRule(ValidateRule rule) {
        rules(rule);
    }

    protected abstract void rules(ValidateRule rule);

    @Override
    public void validateResolved() throws ValidationException, UnauthorizedException {

        prepareForValidation();

        if (!authorize()) {
            failedAuthorization();
        }

        Validator validatorInstance = getValidatorInstance();

        if (validatorInstance.fails()) {
            failedValidation(validator);
        }

        passedValidation();
    }

    /**
     * Prepare the data for validation.
     */
    protected void prepareForValidation() {
    }

    /**
     * Handle a passed validation attempt.
     */
    protected void passedValidation() {
    }

    protected Map<String, String> messages() {
        return new HashMap<>();
    }

    protected Map<String, String> attributes() {
        return new HashMap<>();
    }

    final protected Validator getValidatorInstance() {
        if (validator != null) {
            return validator;
        }

        Factory factory = container.getBean(Factory.class);

        Validator validator = createDefaultValidator(factory);

        setValidator(validator);

        return this.validator;
    }

    final protected Validator createDefaultValidator(Factory factory) {

        ValidateRule validateRule = container.getBean(ValidateRule.class);

        execRule(validateRule);

        return factory.make(validationData(), validateRule, messages(), attributes());
    }

    private Map<String, Object> validationData() {
        return new HashMap<>();
    }

    private void setValidator(Validator validator) {
        this.validator = validator;
    }

    private <T> boolean isSubClassOf(Class<T> type) {
        return type.isInstance(this);
    }

}
