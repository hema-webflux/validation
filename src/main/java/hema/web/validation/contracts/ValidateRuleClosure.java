package hema.web.validation.contracts;

import java.util.Map;

@FunctionalInterface
public interface ValidateRuleClosure {

    <T> void validate(String attribute, T value, Map<String, Object> parameters);
}
