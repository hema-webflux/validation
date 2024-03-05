package hema.web.validation.contracts;

import hema.web.validation.exception.ValidationException;
import hema.web.validation.support.ValidateMessageBag;

import java.util.Map;

public interface Validator {

    Map<String, Object> validated() throws ValidationException;

    Map<String, String[]> rules();

    <T> T getModel(Class<? extends T> kind);

    boolean fails();

    String[] field();

    ValidateMessageBag errors();

}
