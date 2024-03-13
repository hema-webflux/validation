package hema.web.validation.contracts.source;

import java.util.function.UnaryOperator;

public interface SimpleSource extends Sourceable<SimpleSource, Object> {

    SimpleSource add(String attribute, UnaryOperator<SourceClause> sourceClause);

    String[] attributes();

    interface Attributable {

        SourceClause getSourceClause(String attribute);

        boolean isSourceClause(String attribute);

        String getMessage();

    }
}
