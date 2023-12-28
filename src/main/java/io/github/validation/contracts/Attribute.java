package io.github.validation.contracts;

import java.util.Map;

public interface Attribute {

    /**
     * Get custom attributes for validator errors.
     *
     * @param map Map
     * @return Map
     */
    Map<String, String> attributes(Map<String, String> map);
}
