package hema.web.validation;

import hema.web.validation.contracts.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import static hema.web.validation.contracts.ValidatesWhenResolved.*;

import java.util.HashMap;
import java.util.Map;

final class ValidatorFactory implements Factory, ApplicationListener<ContextRefreshedEvent> {

    private final Map<String, String> fallbackMessages = new HashMap<>();

    private final Map<String, ValidateClosure> extensions = new HashMap<>();

    private ApplicationContext applicationContext;

    public ValidatorFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Validator make(Map<String, Object> data, ValidateRule validateRule, Message message, Attribute attribute) {



        return null;
    }

    @Override
    public void extend(String rule, ValidateClosure validateClosure, String message) {

        extensions.put(rule, validateClosure);

        if (message != null) {
            fallbackMessages.put(rule, message);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.applicationContext = event.getApplicationContext();
    }
}
