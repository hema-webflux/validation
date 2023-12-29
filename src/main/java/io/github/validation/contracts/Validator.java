package io.github.validation.contracts;

import io.github.validation.exception.ValidationException;
import io.github.validation.support.MessageBag;

import java.util.Map;

public interface Validator {

    Map<String, Object> validated() throws ValidationException;

    <T> T model();

    boolean fails();

    String[] field();

    MessageBag errors();

}
