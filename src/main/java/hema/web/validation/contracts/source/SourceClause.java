package hema.web.validation.contracts.source;

public interface SourceClause extends Sourceable<SourceClause, String> {

    SourceClause add(String rule, String message);

    boolean hasRule(String rule);
}
