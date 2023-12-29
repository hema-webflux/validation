package io.github.validation;

class UserValidator extends FormValidator {

    public UserValidator() {
        rules(new ValidateRule());
    }

    @Override
    protected void rules(ValidateRule rule) {
        rule.field("name").required().exists("users", "id").nullable()
                .field("avatar").required().unique("users", "name").nullable()
                .field("password").required().json().array();
    }

    @Override
    protected boolean authorize() {
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        new UserValidator();
    }
}