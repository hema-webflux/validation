package hema.web.validation.concerns;

import hema.web.inflector.Inflector;
import hema.web.validation.concerns.haystack.Haystack;
import hema.web.validation.contracts.*;
import hema.web.validation.contracts.translation.Translation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Map;

final class ValidatorFactory implements Factory, ApplicationListener<ContextRefreshedEvent> {

    private final Map<String, String> fallbackMessages;

    private final Map<String, Factory.CustomValidateRulePredicate> extensions;

    private ApplicationContext context;

    public ValidatorFactory(ApplicationContext context, Map<String, String> fallbackMessages, Map<String, Factory.CustomValidateRulePredicate> extensions) {
        this.context = context;
        this.fallbackMessages = fallbackMessages;
        this.extensions = extensions;
    }

    @Override
    public hema.web.validation.contracts.Validator make(
            Map<String, Object> data,
            ValidateRule validateRule,
            Haystack<Object> messages,
            Haystack<String> attributes
    ) {
        return new Validator(
                data,
                validateRule.rules(),
                messages,
                attributes,
                attributes,
                context.getBean(Inflector.class),
                context.getBean(Translation.class)
        );
    }

    @Override
    public void extend(String rule, Factory.CustomValidateRulePredicate closure, String message) {
        extensions.put(rule, closure);
        fallbackMessages.put(rule, message);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.context = event.getApplicationContext();
    }
}