package hema.web.validation.concerns;

import hema.web.validation.concerns.haystack.AttributeHaystack;
import hema.web.validation.concerns.haystack.Haystack;
import hema.web.validation.message.Str;

import java.util.regex.Pattern;

public interface FormatsMessages {

    String replacePlaceholderInString(String attribute);

    /**
     * Get the inline message for a rule if it exists.
     *
     * @param attribute String
     * @param lowerRule String
     * @param haystack  SimpleSource
     * @return String
     */
    default String getFromLocalArray(String attribute, String lowerRule, Haystack<Object> haystack) {

        String[] searches = {String.format("%s.%s", attribute, lowerRule), lowerRule, attribute};

        for (String search : searches) {

            for (String sourceKey : haystack.needles()) {

                if (sourceKey.contains("*")) {

                    String pattern = Str.regexQuote(sourceKey, "#");

                    pattern = pattern.replace("\\*", "([^.]*)");

                    if (Pattern.compile("^" + pattern + "\\z").matcher(search).matches()) {
                        String needle = sourceKey + "." + lowerRule;
                        return haystack.hasNeedleInHaystack(needle)
                                ? (String) haystack.getFromHaystack(needle)
                                : (String) haystack.getFromHaystack(sourceKey);
                    }

                    continue;
                }

                if (Str.is(sourceKey, search)) {

                    String needle = sourceKey + "." + lowerRule;

                    return sourceKey.equals(attribute) && haystack.hasNeedleInHaystack(needle)
                            ? (String) haystack.getFromHaystack(needle)
                            : (String) haystack.getFromHaystack(sourceKey);
                }

            }

        }

        return "";
    }

    default String replaceAttributePlaceholder(String message, String value) {
        return message.replace(":attribute", value)
                .replace(":ATTRIBUTE", value.toUpperCase())
                .replace(":Attribute", Str.ucFirst(value));
    }

}
