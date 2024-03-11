package hema.web.validation.contracts;

import org.springframework.lang.Nullable;
import hema.web.validation.concerns.schema.Blueprint;

import java.util.Map;

public interface Factory {

    Validator make(Map<String, Object> data, ValidateRule validateRule, Blueprint messages, Blueprint attributes);

    void extend(String rule, ValidateClosure validateClosure, @Nullable String message);

}
