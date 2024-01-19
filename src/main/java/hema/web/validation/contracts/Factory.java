package hema.web.validation.contracts;

import org.springframework.lang.Nullable;

import static hema.web.validation.contracts.ValidatesWhenResolved.*;

import java.util.Map;
import java.util.Set;

public interface Factory {

    Validator make(Map<String, Object> data, ValidateRule validateRule, @Nullable Message message, @Nullable Attribute attribute);

    void extend(String rule, ValidateClosure validateClosure, @Nullable String message);

}
