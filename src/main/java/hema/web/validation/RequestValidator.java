package hema.web.validation;

import hema.web.validation.concerns.Factory;
import hema.web.validation.concerns.ValidateRule;
import hema.web.validation.concerns.Validator;
import hema.web.validation.concerns.ValidatorAwareRule;
import hema.web.validation.exception.UnauthorizedException;
import hema.web.validation.exception.ValidationException;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.HashMap;
import java.util.Map;

public abstract class RequestValidator implements ValidatesWhenResolved, ValidatorAwareRule,
        InitializingBean, ApplicationListener<ContextRefreshedEvent> {

    private Validator validator = null;

    protected boolean stopOnFirstFailure = false;

    @Resource
    private ApplicationContext container;

    @Override
    public void afterPropertiesSet() throws ValidationException, UnauthorizedException {
        validateResolved();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        container = event.getApplicationContext();
    }

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

        return factory.make(validationData(), validateRule, messages(), attributes())
                .stopOnFirstFailure(stopOnFirstFailure);
    }

    protected Message messages() {
        return new Message(null, null, true);
    }

    protected Attribute attributes() {
        return new Attribute(null, true);
    }

    private Map<String, Object> validationData() {
        return new HashMap<>();
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    protected abstract boolean authorize();

    protected abstract void rules(ValidateRule rule);
}
