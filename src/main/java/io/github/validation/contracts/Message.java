package io.github.validation.contracts;

import java.util.Map;

public interface Message {

    /**
     * Get custom messages for validator errors.
     *
     * @param map Map
     * @return Map
     */
    Map<String, String> messages(Map<String, String> map);
}
