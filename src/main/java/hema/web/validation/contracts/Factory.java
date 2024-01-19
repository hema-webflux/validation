package hema.web.validation.contracts;

import org.springframework.lang.Nullable;

import static hema.web.validation.contracts.ValidatesWhenResolved.*;

import java.util.Map;

public interface Factory {

    Validator make(Map<String, Object> data, ValidateRule validateRule, Message message, Attribute attribute);

    void extend(String rule, ValidateClosure validateClosure, @Nullable String message);

}
