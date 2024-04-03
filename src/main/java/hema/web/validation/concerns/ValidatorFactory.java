package hema.web.validation.concerns;

import hema.web.inflector.Inflector;
import hema.web.validation.concerns.haystack.Haystack;
import hema.web.validation.contracts.*;
import hema.web.validation.contracts.translation.Translation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;

import java.util.Map;

final class ValidatorFactory implements Factory, ApplicationListener<ContextRefreshedEvent> {

    private Map<String, String> fallbackMessages = null;

    private Map<String, Factory.CustomValidateRulePredicate> extensions = null;

    private ApplicationContext context;

    public ValidatorFactory(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public hema.web.validation.contracts.Validator make(
            Map<String, Object> data,
            ValidateRule validateRule,
            Haystack<Object> messages,
            Haystack<String> attributes
    ) {
        return new ValidatorBean(
                context,
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
    public void extend(@NonNull  String rule, Factory.CustomValidateRulePredicate closure, @NonNull String message) {
        extensions.put(rule, closure);
        fallbackMessages.put(rule, message);
    }

    @Override
    public void setFallbackMessages(Map<String, String> fallbackMessages) {
        this.fallbackMessages = fallbackMessages;
    }

    @Override
    public void setExtensions(Map<String, CustomValidateRulePredicate> extensions) {
        this.extensions = extensions;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.context = event.getApplicationContext();
    }
}