package hema.web.validation.concerns;

import hema.web.validation.contracts.source.SimpleSource;
import hema.web.validation.message.Str;

import java.util.regex.Pattern;

public interface FormatsMessages {

    String replacePlaceholderInString(String attribute);

    /**
     * Get the inline message for a rule if it exists.
     *
     * @param attribute String
     * @param lowerRule String
     * @param sources   SimpleSource
     * @return String
     */
    default String getFromLocalArray(String attribute, String lowerRule, SimpleSource sources) {

        String[] searches = {String.format("%s.%s", attribute, lowerRule), lowerRule, attribute};

        for (String search : searches) {

            for (String sourceKey : sources.attributes()) {

                if (sourceKey.contains("*")) {

                    String pattern = Str.regexQuote(sourceKey, "#");

                    pattern = pattern.replace("\\*", "([^.]*)");

                    if (Pattern.compile("^" + pattern + "\\z").matcher(search).matches()) {
                        return isSourceClause(sourceKey, lowerRule, sources)
                                ? sources.getSourceClause(sourceKey).getSource(lowerRule)
                                : sources.getSource(sourceKey);
                    }

                    continue;
                }

                if (Str.is(sourceKey, search)) {
                    return sourceKey.equals(attribute) && isSourceClause(sourceKey, lowerRule, sources)
                            ? sources.getSourceClause(sourceKey).getSource(lowerRule)
                            : sources.getSource(sourceKey);
                }

            }

        }

        return "";
    }

    /**
     * Determine that the property binding value is the SourceClause instance and that the validation rule exists.
     *
     * @param attribute String
     * @param rule      String
     * @param sources   SimpleSource
     * @return boolean
     */
    private boolean isSourceClause(String attribute, String rule, SimpleSource sources) {
        return sources.isSourceClause(attribute) && sources.getSourceClause(attribute).hasRule(rule);
    }

    default String replaceAttributePlaceholder(String message, String value) {
        return message.replace(":attribute", value)
                .replace(":ATTRIBUTE", value.toUpperCase())
                .replace(":Attribute", Str.ucFirst(value));
    }

}
