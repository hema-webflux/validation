package io.github.validation.contacts;

import java.util.Map;

public interface Message {

    /**
     * Define error messages.
     *
     * @param map Map
     * @return Map
     */
    Map<String,String> messages(Map<String,String> map);
}
