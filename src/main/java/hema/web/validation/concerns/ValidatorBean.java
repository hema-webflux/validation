package hema.web.validation.concerns;

import hema.web.inflector.Inflector;
import hema.web.validation.concerns.haystack.Haystack;
import hema.web.validation.contracts.MessageBag;
import hema.web.validation.contracts.Validator;
import hema.web.validation.translation.Translation;
import hema.web.validation.exception.ValidationException;
import hema.web.validation.message.Str;
import org.springframework.context.ApplicationContext;

import java.util.*;

final class ValidatorBean extends ValidateRules implements Validator, ValidateAttributes, FormatsMessages {

    private ApplicationContext applicationContext;

    private final Translation translator;

    private final Inflector inflector;

    private Map<String, String> fallbackMessage;

    private Map<String, String> replacers;

    private Map<String, Validator.ValidateRulePredicate> extensions;

    private final Map<String, Object> data;

    private final Haystack<Object> messages;

    private final Haystack<String> attributes;

    private final Map<String, Object[]> initialRules;

    private Set<ValidateHookConsumer> after;

    private final String dotPlaceholder;

    private MessageBag messageBag = null;

    ValidatorBean(Translation translator, Inflector inflector, Map<String, Object> data, Map<String, Object[]> rules,
                  Haystack<Object> messages, Haystack<String> attributes) {

        this.dotPlaceholder = Str.random(16);

        this.translator = translator;
        this.inflector = inflector;
        this.data = data;
        this.initialRules = rules;
        this.messages = messages;
        this.attributes = attributes;
    }

    private void validateAttribute(String attribute, Object[] parameters) {

        if (parameters.length == 0) {
            return;
        }

        if (dependentRules.contains((String) parameters[0])) {

        }

        Object value = data.get(attribute);

        boolean validatable = isValidatable((String) parameters[0], attribute, value);

    }

    private String[] replaceDotInParameters(String[] parameters) {
        return Arrays.stream(parameters)
                .map(attribute -> attribute.replace("\\.", dotPlaceholder))
                .toArray(String[]::new);
    }

    private boolean isValidatable(String rule, String attribute, Object value) {

        if (hasRule(rule, excludeRules)) {
            return true;
        }

        return false;
    }

    /**
     * Determine if the field is present, or the rule implies required.
     *
     * @param rule      Validate rule.
     * @param attribute Field.
     * @param value     Input value.
     * @return Boolean
     */
    private boolean presentOrRuleIsImplicit(String rule, String attribute, Object value) {

        if (validateString(value) && ((String) value).isBlank()) {
            return isImplicit(rule);
        }

        return validatePresent(attribute) || isImplicit(rule);
    }

    @Override
    public Map<String, Object> validated() throws ValidationException {
        return null;
    }

    @Override
    public Map<String, String[]> rules() {
        return null;
    }

    @Override
    public boolean fails() {
        return false;
    }

    @Override
    public String[] field() {
        return new String[0];
    }

    @Override
    public MessageBag errors() {
        return messageBag;
    }

    @Override
    public void after(ValidateHookConsumer consumer) {

        if (Objects.isNull(this.after)) {
            this.after = new HashSet<>();
        }

        consumer.accept(this);

        this.after.add(consumer);
    }

    public boolean passes() {
        messageBag = applicationContext.getBean(MessageBag.class);

        return messageBag.isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getValue(String attribute) {
        return (T) data.get(attribute);
    }

    @Override
    public boolean validatePresent(String attribute) {
        return data.containsKey(attribute);
    }

    public String getMessage(String attribute, String rule) {

        String attributeWithPlaceholders = attribute;

        attribute = replacePlaceholderInString(attribute);

        String inlineMessage = getFromLocalArray(attribute, rule, messages);

        if (!inlineMessage.isEmpty()) {
            return inlineMessage;
        }

        String lowerRule = inflector.snake(rule, "_");

        String customKey = STR."validation.custom.\{attribute}.\{lowerRule}";

        String customMessage = getCustomMessageFromTranslator(
                hasRule(rule, sizeRules)
                        ? new String[]{STR."\{customKey}.\{getAttributeType(attribute)}", customKey}
                        : new String[]{customKey}
        );

        if (!customMessage.equals(customKey)) {
            return customMessage;
        } else if (hasRule(rule, sizeRules)) {
            return getSizeMessage(attributeWithPlaceholders, rule);
        }

        String translatorKey = STR."validation.\{lowerRule}";

        if (!translatorKey.equals(translator.get(translatorKey, "en"))) {
            return translator.get(translatorKey, "en");
        }

        String message = getFromLocalArray(attribute, lowerRule, fallbackMessage);

        return message.isEmpty() ? translatorKey : message;
    }

    private String getSizeMessage(String attribute, String rule) {

        String lowerRule = inflector.snake(rule, "_");

        String translatorKey = STR."validation.\{lowerRule}.\{getAttributeType(attribute)}";

        return translator.get(translatorKey, "en");
    }

    private String getCustomMessageFromTranslator(String[] keys) {
        return "";
    }

    private String getAttributeType(String attribute) {

        if (hasRule(attribute, numericRule)) {
            return "numeric";
        }

        return "string";
    }

    @Override
    public String replacePlaceholderInString(String attribute) {
        return attribute.replace(dotPlaceholder, ".")
                .replace("__asterisk__", "*");
    }

    public void addFailure(String attribute, String rule) {

        if (Objects.isNull(messageBag)) {
            passes();
        }

        String attributeWithPlaceholders = attribute;

        attribute = replacePlaceholderInString(attribute);


    }

    void setFallbackMessage(Map<String, String> fallbackMessage) {
        this.fallbackMessage = fallbackMessage;
    }

    void setApplication(ApplicationContext application) {
        this.applicationContext = application;
    }

    void addImplicitExtensions(Map<String, Validator.ValidateRulePredicate> implicitRules) {
        addExtensions(implicitRules);
        this.implicitRules.addAll(implicitRules.keySet());
    }

    void addDependentExtensions(Map<String, Validator.ValidateRulePredicate> dependentRules) {
        addExtensions(dependentRules);
        this.dependentRules.addAll(dependentRules.keySet());
    }

    void addExtensions(Map<String, Validator.ValidateRulePredicate> extensions) {
        if (Objects.nonNull(extensions)) {
            this.extensions.putAll(extensions);
        }
    }

    void addReplacers(Map<String, String> replacers) {
        if (Objects.nonNull(replacers)) {
            this.replacers.putAll(replacers);
        }
    }
}
