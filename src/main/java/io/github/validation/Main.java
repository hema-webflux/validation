package io.github.validation;

import io.github.validation.contracts.Attribute;
import io.github.validation.contracts.Message;
import io.github.validation.contracts.ValidateRule;

import java.util.Map;

class UserValidator extends FormValidator implements Attribute, Message {

    @Override
    public Map<String, String> attributes(Map<String, String> map) {
        return null;
    }

    @Override
    public Map<String, String> messages(Map<String, String> map) {
        return null;
    }

    @Override
    protected void rules(ValidateRule validateRule) {

    }

    @Override
    protected boolean authorize() {
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        FormValidator userValidator = new UserValidator();

        System.out.println(userValidator.isSubClassOf(Message.class));

    }
}