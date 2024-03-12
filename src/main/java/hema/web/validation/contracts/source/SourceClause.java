package hema.web.validation.contracts.source;

public interface SourceClause extends Sourceable {

    SourceClause add(String rule, String message);

    @Override
    String getSource(String attribute);
}
