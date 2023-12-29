package io.github.validation;

import java.util.*;

final public class ValidateRule {

    private String field;

    private StringBuilder builder = null;

    public final Map<String, Set<Object>> rules = new HashMap<>();

    public ValidateRule field(String name) {
        if (!rules.containsKey(name)) {
            rules.put(name, new HashSet<>());
            field = name;
        }

        return this;
    }

    public ValidateRule required() {
        return store("required");
    }

//    public ValidateRule requiredIf(String ...rules) {
//        return buffer("required_if:", rules);
//    }

    public ValidateRule unique(String table, String column) {
        return buffer("unique:", table, column);
    }

    public ValidateRule exists(String table, String column) {
        return buffer("exists:", table, column);
    }

    public ValidateRule nullable() {
        return store("nullable");
    }

    public ValidateRule max(int value) {
        return buffer("max:", Integer.toString(value));
    }

    public ValidateRule min(int value) {
        return buffer("min:", Integer.toString(value));
    }

    public ValidateRule numeric() {
        return store("numeric");
    }

    public ValidateRule integer() {
        return store("integer");
    }

    public ValidateRule map() {
        return store("map");
    }

    public ValidateRule array() {
        return store("array");
    }

    public ValidateRule string() {
        return store("string");
    }

    public ValidateRule json() {
        return store("json");
    }

    public ValidateRule url() {
        return store("url");
    }

    public ValidateRule image() {
        return store("image");
    }

    public ValidateRule buffer(String... values) {

        StringBuilder builder = getBuilder();

        String last = values[values.length - 1];

        Arrays.stream(values).forEach(rule -> {
            builder.append(rule.equals(last) || rule.endsWith(":") ? rule : rule + ",");
        });

        store(builder.toString());

        builder.setLength(0);

        return this;
    }

    private StringBuilder getBuilder() {

        if (builder != null) {
            return builder;
        }

        return builder = new StringBuilder();
    }

    private ValidateRule store(String value) {
        rules.get(field).add(value);
        return this;
    }

}
