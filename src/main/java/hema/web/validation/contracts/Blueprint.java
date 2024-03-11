package hema.web.validation.contracts;

import java.util.Map;
import java.util.function.UnaryOperator;

public interface Blueprint {

    Blueprint add(String attribute, String value);

    <T> Blueprint add(String attribute, UnaryOperator<T> closure);

    void setStore(Map<String, Object> map);

    default boolean isClause() {
        return false;
    }
}
