package hema.web.validation.concerns;

import hema.web.inflector.Inflector;
import hema.web.validation.concerns.haystack.Haystack;
import hema.web.validation.contracts.*;
import hema.web.validation.contracts.translation.Translation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

final class ValidatorFactory implements Factory, ApplicationListener<ContextRefreshedEvent> {

    private final Map<String, String> fallbackMessages;

    private Map<String, Factory.CustomValidateRulePredicate> extensions = null;

    private ApplicationContext context;

    public ValidatorFactory(ApplicationContext context, Map<String, String> fallbackMessages) {
        this.context = context;
        this.fallbackMessages = fallbackMessages;
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
    public void extend(String rule, Factory.CustomValidateRulePredicate closure, String message) {

        if (Objects.isNull(extensions)) {
            extensions = new HashMap<>();
        }

        extensions.put(rule, closure);
        fallbackMessages.put(rule, message);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.context = event.getApplicationContext();
    }
}