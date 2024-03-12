package hema.web.validation;

import hema.web.validation.concerns.store.AttributeSource;
import hema.web.validation.concerns.store.MessageSource;
import hema.web.validation.contracts.*;
import hema.web.validation.contracts.source.SimpleSource;
import hema.web.validation.contracts.source.Sourceable;
import hema.web.validation.exception.UnauthorizedException;
import hema.web.validation.exception.ValidationException;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public abstract class FormValidator implements ValidatesWhenResolved {

    private Validator validator = null;

    @Resource
    private ApplicationContext container;

    protected abstract boolean authorize();

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

    /**
     * Get custom messages for validator errors.
     *
     * @param messageSource SimpleSource
     * @return SimpleSource
     */
    protected SimpleSource messages(SimpleSource messageSource) {
        return messageSource;
    }

    /**
     * Get custom attributes for validator errors.
     *
     * @param attributeSource Sourceable
     * @return Sourceable
     */
    protected Sourceable attributes(Sourceable attributeSource) {
        return attributeSource;
    }

    final protected Validator getValidatorInstance() {
        if (validator != null) {
            return validator;
        }

        Factory factory = container.getBean(Factory.class);

        Validator validator = createDefaultValidator(factory);

        setValidator(validator);

        return validator;
    }

    final protected Validator createDefaultValidator(Factory factory) {

        ValidateRule validateRule = container.getBean(ValidateRule.class);

        this.rules(validateRule);

        return factory.make(
                validationData(),
                validateRule,
                messages(new MessageSource()),
                attributes(new AttributeSource())
        );
    }

    private Map<String, Object> validationData() {
        return new HashMap<>();
    }

    private void setValidator(Validator validator) {
        this.validator = validator;
    }
}
