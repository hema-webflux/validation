package hema.web.validation.contracts.source;

import java.util.function.UnaryOperator;

public interface SimpleSource extends Sourceable {

    SimpleSource add(String attribute, String value);

    SimpleSource add(String attribute, UnaryOperator<SourceClause> sourceClause);

    boolean isSourceClause(String attribute);
}
