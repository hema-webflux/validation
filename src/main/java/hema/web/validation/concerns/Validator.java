package hema.web.validation.concerns;

import hema.web.validation.contracts.ValidateRule;
import hema.web.validation.contracts.message.MessageBag;
import hema.web.validation.exception.ValidationException;
import hema.web.validation.support.ValidateMessageBag;

import static hema.web.validation.contracts.ValidatesWhenResolved.*;

import java.util.Map;

final class Validator implements hema.web.validation.contracts.Validator, ValidateAttributes {

    private final Map<String, Object> data;

    private final Message customMessage;

    private final Attribute customAttribute;

    private final Map<String, String[]> initialRules;

    private String currentRule = null;

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

    Validator(Map<String, Object> data, Map<String, String[]> rules, Message message, Attribute attribute) {
        this.data = data;
        this.initialRules = rules;
        this.customMessage = message;
        this.customAttribute = attribute;
    }

    private void validateAttribute(String attribute, ValidateRule.Access access) {

        if (access.rule().isEmpty()) {
            return;
        }

        currentRule = access.rule();

        Object value = data.get(attribute);

        boolean validatable = isValidatable(access.rule(), attribute, value);

    }

    private boolean isValidatable(String rule, String attribute, Object value) {

        return false;
    }

    /**
     * @param rule String
     * @return boolean
     */
    private boolean dependsOnOtherFields(String rule) {
        return true;
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
    public <T> T getModel(Class<T> kind) {
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
        messageBag = new ValidateMessageBag(null, this);

        return messageBag.isEmpty();
    }
}
