package hema.web.validation.concerns.store;

import hema.web.validation.contracts.source.Sourceable;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractSource implements Sourceable {

    protected final Map<String, Object> source = new HashMap<>();

    @Override
    public boolean isEmpty() {
        return source.isEmpty();
    }

    @Override
    public boolean hasKey(String value) {
        return source.containsKey(value);
    }
}