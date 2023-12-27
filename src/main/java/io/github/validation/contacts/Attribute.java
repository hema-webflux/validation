package io.github.validation.contacts;

import java.util.Map;

public interface Attribute {

    /**
     * Define field attribute.
     *
     * @param map Map
     * @return Map
     */
    Map<String,String> attributes(Map<String,String> map);
}
