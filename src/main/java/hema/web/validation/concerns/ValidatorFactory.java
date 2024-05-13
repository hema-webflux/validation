package hema.web.validation.concerns;

import hema.web.inflector.Inflector;
import hema.web.validation.concerns.verifier.DatabasePresenceVerifier;
import hema.web.validation.concerns.translation.Translation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Map;
import java.util.Objects;

final class ValidatorFactory implements Factory, ApplicationListener<ContextRefreshedEvent> {

    private Resolver resolver = null;

    private ApplicationContext application;

    private final Translation translation;

    private Map<String, String> replacers = null;

    private Map<String, String> fallbackMessages = null;

    private Map<String, Validator.ValidateRulePredicate> extensions = null;

    private Map<String, Validator.ValidateRulePredicate> implicitExtensions = null;

    private Map<String, Validator.ValidateRulePredicate> dependents = null;

    private DatabasePresenceVerifier verifier = null;

    public ValidatorFactory(ApplicationContext application, Translation translation) {
        this.application = application;
        this.translation = translation;
    }

    @Override
    public Validator make(Map<String, Object> data, ValidateRule validateRule, Haystack<Object> messages, Haystack<String> attributes) {

        ValidatorBean validator = (ValidatorBean) resolve(data, validateRule, messages, attributes);

        if (Objects.nonNull(verifier)) {
            validator.setPresenceVerifier(verifier);
        }

        validator.setApplication(this.application);

        addExtensions(validator);

        return validator;
    }

    private void addExtensions(ValidatorBean validator) {

        validator.addExtensions(extensions);

        validator.addImplicitExtensions(implicitExtensions);

        validator.addDependentExtensions(dependents);

        validator.addReplacers(replacers);

        validator.setFallbackMessage(fallbackMessages);
    }

    @Override
    public void extend(String rule, Validator.ValidateRulePredicate closure, String message) {
        extensions.put(rule, closure);
        if (Objects.nonNull(message)) {
            fallbackMessages.put(rule, message);
        }
    }

    @Override
    public void extendImplicit(String rule, Validator.ValidateRulePredicate closure, String message) {
        implicitExtensions.put(rule, closure);
        if (Objects.nonNull(message)) {
            fallbackMessages.put(rule, message);
        }
    }

    @Override
    public void extendDependent(String rule, Validator.ValidateRulePredicate closure, String message) {
        dependents.put(rule, closure);
        if (Objects.nonNull(message)) {
            fallbackMessages.put(rule, message);
        }
    }

    private Validator resolve(Map<String, Object> data, ValidateRule rule, Haystack<Object> messages, Haystack<String> attributes) {

        Inflector inflector = application.getBean(Inflector.class);

        if (Objects.isNull(resolver)) {
            return new ValidatorBean(translation, inflector, data, rule.rules(), messages, attributes);
        }

        return resolver.apply(translation, inflector, data, rule, messages, attributes);
    }

    public void resolver(Resolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.application = event.getApplicationContext();
    }

    void setPresenceVerifier(DatabasePresenceVerifier presenceVerifier) {
        this.verifier = presenceVerifier;
    }
}