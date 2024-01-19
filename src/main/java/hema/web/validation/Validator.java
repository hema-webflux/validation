package hema.web.validation;

import hema.web.validation.exception.ValidationException;
import hema.web.validation.support.MessageBag;

import static hema.web.validation.contracts.ValidatesWhenResolved.*;

import java.util.Map;

final class Validator implements hema.web.validation.contracts.Validator {

    private final Map<String, Object> data;

    private final Message customMessage;

    private final Attribute customAttribute;

    private final Map<String, String[]> initialRules;

    Validator(Map<String, Object> data, Map<String, String[]> rules, Message message, Attribute attribute) {
        this.data = data;
        this.initialRules = rules;
        this.customMessage = message;
        this.customAttribute = attribute;
    }

    @Override
    public Map<String, Object> validated() throws ValidationException {
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
    public MessageBag errors() {
        return null;
    }
}
