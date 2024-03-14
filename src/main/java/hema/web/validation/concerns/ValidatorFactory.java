package hema.web.validation.concerns;

import hema.web.validation.concerns.haystack.AttributeHaystack;
import hema.web.validation.concerns.haystack.Haystack;
import hema.web.validation.concerns.haystack.MessageHaystack;
import hema.web.validation.contracts.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Map;

final class ValidatorFactory implements Factory,ApplicationListener<ContextRefreshedEvent> {

    private final Map<String, String> fallbackMessages;

    private final Map<String, ValidateClosure> extensions;

    private ApplicationContext applicationContext;

    public ValidatorFactory(ApplicationContext applicationContext, Map<String, String> fallbackMessages, Map<String, ValidateClosure> extensions) {
        this.applicationContext = applicationContext;
        this.fallbackMessages = fallbackMessages;
        this.extensions = extensions;
    }

    @Override
    @SuppressWarnings("unchecked")
    public hema.web.validation.contracts.Validator make(Map<String, Object> data, ValidateRule validateRule, Haystack<?, Object> messages, Haystack<?, String> attributes) {
        return new Validator(
                data,
                validateRule.rules(),
                (Haystack<MessageHaystack, Object>) messages,
                (Haystack<AttributeHaystack, String>) attributes
        );
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
