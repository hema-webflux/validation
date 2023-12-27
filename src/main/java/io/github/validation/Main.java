package io.github.validation;

import io.github.validation.contacts.Attribute;
import io.github.validation.contacts.Message;

import java.util.Map;

class UserValidator extends Validator implements Attribute, Message {

    @Override
    protected void rules() {

    }

    @Override
    public Map<String, String> attributes(Map<String, String> map) {
        return null;
    }

    @Override
    public Map<String, String> messages(Map<String, String> map) {
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Validator userValidator = new UserValidator();

        System.out.println(userValidator.isSubClassOf(Message.class));

    }
}