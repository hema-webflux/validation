package hema.web.validation.concerns;

import hema.web.validation.concerns.schema.Blueprint;
import hema.web.validation.message.Str;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public interface FormatsMessages {

    String replacePlaceholderInString(String attribute);

    default String getFromLocalArray(String attribute, String lowerRule, Blueprint sources) {

        String[] searches = {String.format("%s.%s", attribute, lowerRule), lowerRule, attribute};

        for (String search : searches) {

            for (String messageKey : sources.getAttributeKeysToArray()) {

                if (messageKey.contains("*")) {

                    String pattern = Str.regexQuote(messageKey, "#");

                    pattern = pattern.replace("\\*", "([^.]*)");

                    Pattern patternMatch = Pattern.compile("^" + pattern + "\\z");

                    if (patternMatch.matcher(search).matches()) {
                        return sources.isCallback(messageKey, lowerRule)
                                ? sources.get(messageKey, Blueprint.class).get(lowerRule, String.class)
                                : sources.get(messageKey, String.class);
                    }

                    continue;
                }

                if (Str.is(messageKey, search)) {
                    return messageKey.equals(attribute) && sources.isCallback(messageKey, lowerRule)
                            ? sources.get(messageKey, Blueprint.class).get(lowerRule, String.class)
                            : sources.get(messageKey, String.class);
                }

            }

        }

        return "";
    }

}
