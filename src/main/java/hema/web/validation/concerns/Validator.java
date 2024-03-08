package hema.web.validation.concerns;

import hema.web.validation.contracts.ValidateRule;
import hema.web.validation.contracts.MessageBag;
import hema.web.validation.exception.ValidationException;
import hema.web.validation.message.ValidateMessageBag;

import java.util.Map;
import java.util.Set;

final class Validator implements hema.web.validation.contracts.Validator, ValidateAttributes {

    private final Map<String, Object> data;

    private final Map<String, String> messages;

    private final Map<String, String> attributes;

    private final Map<String, Set<ValidateRule.Access>> initialRules;

    private MessageBag messageBag = null;

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

    Validator(Map<String, Object> data, Map<String, Set<ValidateRule.Access>> rules, Map<String, String> messages, Map<String, String> attributes) {
        this.data = data;
        this.initialRules = rules;
        this.messages = messages;
        this.attributes = attributes;
    }

    private void validateAttribute(String attribute, ValidateRule.Access access) {

        if (access.other().isEmpty()) {
            return;
        }

        if (dependsOnOtherFields(access.other())) {

        }

        Object value = data.get(attribute);

        boolean validatable = isValidatable(access.other(), attribute, value);

    }

    private String[] replaceDotInParameters(String[] parameters) {

        // 循环parameters
        // 如果parameter包含\.，如： password\.
        // 则将 \. 替换为 this.dotPlaceholder

        return new String[]{};
    }

    private boolean isValidatable(String rule, String attribute, Object value) {

        return false;
    }

    private <T> String tryConvertToString(T value) {
        try {
            return String.valueOf(value);
        } catch (Exception e) {
            return null;
        }
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
    public <T> T getModel(Class<? extends T> kind) {
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
    public ValidateMessageBag errors() {
        return null;
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
}
