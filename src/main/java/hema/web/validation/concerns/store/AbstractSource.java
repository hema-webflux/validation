package hema.web.validation.concerns.store;

import hema.web.validation.contracts.source.Sourceable;

import java.util.HashMap;
import java.util.Map;

sealed abstract class AbstractSource<T, R> implements Sourceable<T, R>
        permits AttributeSource, MessageSource, MessageSource.RuleMessageClause {

    protected final Map<String, R> source = new HashMap<>();

    @Override
    public R getSource(String attribute) {
        return source.get(attribute);
    }

    @Override
    public boolean isEmpty() {
        return source.isEmpty();
    }

    @Override
    public boolean hasAttribute(String value) {
        return source.containsKey(value);
    }
}
