package hema.web.validation.contracts.source;

public interface SourceClause extends Sourceable {

    SourceClause add(String rule, String message);

    boolean hasRule(String rule);

    @Override
    String getSource(String rule);
}
