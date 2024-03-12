package hema.web.validation.concerns.store;

import hema.web.validation.contracts.source.SimpleSource;
import hema.web.validation.contracts.source.SourceClause;

import java.util.HashSet;
import java.util.Set;
import java.util.function.UnaryOperator;

public class MessageSource extends AbstractSource implements SimpleSource {

    private final Set<String> fallbacks = new HashSet<>();

    @Override
    public SimpleSource add(String attribute, String value) {
        source.put(attribute, value);
        return this;
    }

    @Override
    public SourceClause getSourceClause(String attribute) {
        return (SourceClause) source.get(attribute);
    }

    @Override
    public SimpleSource add(String attribute, UnaryOperator<SourceClause> sourceClause) {
        source.put(attribute, sourceClause.apply(new RuleMessageClause()));
        fallbacks.add(attribute);
        return this;
    }

    @Override
    public boolean isSourceClause(String attribute) {
        return fallbacks.contains(attribute);
    }

    @Override
    public String[] attributes() {
        return source.keySet().toArray(new String[0]);
    }

    private static class RuleMessageClause extends AbstractSource implements SourceClause {

        @Override
        public SourceClause add(String rule, String message) {
            source.put(rule, message);
            return this;
        }

        @Override
        public boolean hasRule(String rule) {
            return source.containsKey(rule);
        }
    }
}
