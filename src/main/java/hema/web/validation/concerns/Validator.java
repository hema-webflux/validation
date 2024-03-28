package hema.web.validation.concerns;

import hema.web.inflector.Inflector;
import hema.web.validation.concerns.haystack.Haystack;
import hema.web.validation.contracts.MessageBag;
import hema.web.validation.contracts.translation.Translator;
import hema.web.validation.exception.ValidationException;
import hema.web.validation.message.Str;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

final class Validator implements hema.web.validation.contracts.Validator, ValidateAttributes, FormatsMessages {

    private final Map<String, Object> data;

    private final Haystack<Object> messages;

    private final Haystack<String> attributes;

    private final Map<String, Object[]> initialRules;

    private final Haystack<String> fallbackMessage;

    private final Translator translator;

    private final String dotPlaceholder;

    private MessageBag messageBag = null;

    private final Inflector inflector;

    private final String[] implicitRules = {
            "Accepted",
            "Required",
            "String",
            "Url",
            "Integer",
            "Numeric",
            "Json",
            "Map",
            "Array",
            "Uppercase",
            "Lowercase",
            "Email",
            "Phone",
            "Date",
            "Bool",
            "IdCard",
            "BankCard"
    };

    private final String[] dependentRules = {
            "Same",
            "Confirmed",
            "After",
            "Before"
    };

    private final String[] sizeRules = {
            "max",
            "min",
            "between"
    };

    private final String[] numericRule = {
            "Integer",
            "Numeric"
    };

    Validator(Map<String, Object> data, Map<String, Object[]> rules,
              Haystack<Object> messages, Haystack<String> attributes, Haystack<String> fallbackMessage, Inflector inflector, Translator translator) {

        this.dotPlaceholder = Str.random(16);

        this.data = data;
        this.initialRules = rules;
        this.messages = messages;
        this.attributes = attributes;
        this.translator = translator;
        this.fallbackMessage = fallbackMessage;

        this.inflector = inflector;
    }

    private void validateAttribute(String attribute, Object[] parameters) {

        if (parameters.length == 0) {
            return;
        }

        if (dependsOnOtherFields((String) parameters[0])) {

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

        return false;
    }

    /**
     * @param rule String
     * @return boolean
     */
    private boolean dependsOnOtherFields(String rule) {

        for (String value : dependentRules) {
            if (rule.equals(value)) {
                return true;
            }
        }

        return false;
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

    public boolean passes() {
        messageBag = new ValidateMessageBag();

        return messageBag.isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getValue(String attribute) {
        return (T) data.get(attribute);
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

        String translatorKey = String.format("validation.%s", lowerRule);

        if (!translatorKey.equals(translator.get(translatorKey))) {
            return translator.get(translatorKey);
        }

        String message = getFromLocalArray(attribute, lowerRule, fallbackMessage);

        return message.isEmpty() ? translatorKey : message;
    }

    private String getSizeMessage(String attribute, String rule) {

        String lowerRule = inflector.snake(rule, "_");

        String translatorKey = STR."validation.\{lowerRule}.\{getAttributeType(attribute)}";

        return translator.get(translatorKey);
    }

    private String getCustomMessageFromTranslator(String[] keys) {

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
}
