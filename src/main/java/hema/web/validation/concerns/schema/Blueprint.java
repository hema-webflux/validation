package hema.web.validation.concerns.schema;

import hema.web.contracts.anonymous.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

public class Blueprint implements Cloneable, Nullable {

    protected Map<String, Object> store = new HashMap<>();

    private final Map<String, Set<String>> fallbacks = new HashMap<>();

    private boolean nullable = false;

    @Override
    public boolean isNullable() {
        return nullable;
    }

    public Blueprint setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public Blueprint add(String attribute, String value) {
        store.put(attribute, value);
        return this;
    }

    public Blueprint add(String attribute, UnaryOperator<BlueprintClause> closure) {
        BlueprintClause clause = closure.apply(clone());

        store.put(attribute, clause);

        fallbacks.put(attribute, clause.store.keySet());

        return this;
    }

    public boolean isCallback(String attribute, String rule) {
        return fallbacks.containsKey(attribute) && fallbacks.get(attribute).contains(rule);
    }

    public <T> T get(String key, Class<T> kind) {
        return kind.cast(store.get(key));
    }

    @Override
    public BlueprintClause clone() {
        try {
            BlueprintClause clone = (BlueprintClause) super.clone();
            clone.store.clear();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static class BlueprintClause extends Blueprint {

        /**
         * Add a message to the rule when validation fails.
         *
         * @param rule    Validate rule,eg: name.require
         * @param message Validate message. eg: The :attribute field is required.
         * @return BlueprintClause
         */
        @Override
        public BlueprintClause add(String rule, String message) {
            store.put(rule, message);
            return this;
        }
    }
}
