package hema.web.validation.contracts;

import org.springframework.lang.Nullable;

import java.util.Map;

public interface Factory<T extends Haystack<T, Object>, U extends Haystack<U, String>> {

    /**
     * Create a new Validator instance.
     *
     * @param data         Input data.
     * @param validateRule Rule proxy.
     * @param messages     Custom rule validate Message sources.
     * @param attributes   Custom attribute name.
     * @return Return new Validator instance.
     */
    Validator make(Map<String, Object> data, ValidateRule validateRule, Haystack<T, Object> messages, Haystack<U, String> attributes);

    void extend(String rule, ValidateClosure validateClosure, @Nullable String message);

}
