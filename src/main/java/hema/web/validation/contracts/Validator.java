package hema.web.validation.contracts;

import hema.web.validation.exception.ValidationException;
import hema.web.validation.support.ValidateMessageBag;

import java.util.Map;

public interface Validator {

    Map<String, Object> validated() throws ValidationException;

    <T> T getModel(Class<T> kind);

    boolean fails();

    String[] field();

    ValidateMessageBag errors();

}
