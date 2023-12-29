package io.github.validation.contracts;

public interface Rule {

    <T> boolean validate(String attribute, T value);

    String message();
}
