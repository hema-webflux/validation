package hema.web.validation;

import hema.web.validation.concerns.haystack.AttributeHaystack;
import hema.web.validation.concerns.haystack.MessageHaystack;
import hema.web.validation.contracts.*;
import hema.web.validation.exception.UnauthorizedException;
import hema.web.validation.exception.ValidationException;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public abstract class RequestValidator implements ValidatesWhenResolved, ValidatorAwareRule {

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
     * @param haystacks Haystack
     */
    protected void messages(MessageHaystack haystacks) {

    }

    /**
     * Get custom attributes for validator errors.
     *
     * @param haystacks Haystack
     */
    protected void attributes(AttributeHaystack haystacks) {
    }

    final protected Validator getValidatorInstance() {

        if (validator != null) {
            return validator;
        }

        Factory factory = container.getBean(Factory.class);

        Validator validator = createDefaultValidator(factory);

        this.setValidator(validator);

        return validator;
    }

    final protected Validator createDefaultValidator(Factory factory) {

        ValidateRule validateRule = container.getBean(ValidateRule.class);

        this.rules(validateRule);

        return factory.make(
                validationData(),
                validateRule,
                new MessageHaystack(new HashMap<>(), new HashSet<>()),
                new AttributeHaystack(new HashMap<>())
        );
    }

    private Map<String, Object> validationData() {
        return new HashMap<>();
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}