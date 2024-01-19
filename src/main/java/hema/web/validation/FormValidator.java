package hema.web.validation;

import hema.web.validation.contracts.Factory;
import hema.web.validation.contracts.ValidateRule;
import hema.web.validation.contracts.ValidatesWhenResolved;
import hema.web.validation.contracts.Validator;
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

    protected abstract ValidateRule rules(ValidateRule rule);

    @Override
    public void validateResolved() throws ValidationException {

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

        ValidateRule validateRule = rules(container.getBean(ValidateRule.class));

        Message message = isSubClassOf(Message.class)
                ? (Message) this
                : new Message.AnonymousMessage();

        Attribute attribute = isSubClassOf(Attribute.class)
                ? (Attribute) this
                : new Attribute.AnonymousAttribute();

        return factory.make(validationData(), validateRule, message, attribute);
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
