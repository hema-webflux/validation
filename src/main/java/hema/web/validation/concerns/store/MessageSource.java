package hema.web.validation.concerns.store;

import hema.web.validation.contracts.source.SimpleSource;
import hema.web.validation.contracts.source.SourceClause;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

public non-sealed class MessageSource extends AbstractSource<SimpleSource, Object> implements SimpleSource {

    private final Map<String, Object> sources = new HashMap<>();

    private final Set<String> fallbacks = new HashSet<>();

    @Override
    public SimpleSource add(String attribute, String value) {
        sources.put(attribute, value);
        return this;
    }

    @Override
    public SimpleSource add(String attribute, UnaryOperator<SourceClause> sourceClause) {
        sources.put(attribute, sourceClause.apply(new RuleMessageClause()));
        fallbacks.add(attribute);
        return this;
    }

    @Override
    public SourceClause getSourceClause(String attribute) {
        return (SourceClause) sources.get(attribute);
    }

    @Override
    public boolean isSourceClause(String attribute) {
        return fallbacks.contains(attribute);
    }

    @Override
    public String[] attributes() {
        return sources.keySet().toArray(new String[0]);
    }

    protected static non-sealed class RuleMessageClause extends AbstractSource<SourceClause, String> implements SourceClause {

        @Override
        public boolean hasRule(String rule) {
            return source.containsKey(rule);
        }

        @Override
        public SourceClause add(String attribute, String value) {
            source.put(attribute, value);
            return this;
        }
    }
}
