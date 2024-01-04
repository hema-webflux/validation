package io.github.validation;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

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

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }
}