package hema.web.validation.contracts;

import hema.web.validation.contracts.source.SimpleSource;
import hema.web.validation.contracts.source.Sourceable;
import org.springframework.lang.Nullable;

import java.util.Map;

public interface Factory {

    /**
     * Create a new Validator instance.
     *
     * @param data         Input data.
     * @param validateRule Rule proxy.
     * @param messages     Custom rule validate Message sources.
     * @param attributes   Custom attribute name.
     * @return Return new Validator instance.
     */
    Validator make(Map<String, Object> data, ValidateRule validateRule, SimpleSource messages, Sourceable attributes);

    void extend(String rule, ValidateClosure validateClosure, @Nullable String message);

}
