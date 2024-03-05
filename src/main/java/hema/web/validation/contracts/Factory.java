package hema.web.validation.contracts;

import org.springframework.lang.Nullable;

import java.util.Map;

public interface Factory {

    Validator make(Map<String, Object> data, ValidateRule validateRule, Map<String,String> messages, Map<String,String> attributes);

    void extend(String rule, ValidateClosure validateClosure, @Nullable String message);

}
