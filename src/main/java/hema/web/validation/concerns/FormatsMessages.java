package hema.web.validation.concerns;

import hema.web.validation.concerns.schema.Blueprint;
import hema.web.validation.message.Str;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public interface FormatsMessages {

    String replacePlaceholderInString(String attribute);

    default String getFromLocalArray(String attribute, String lowerRule, Blueprint blueprint) {

        String[] searches = {String.format("%s.%s", attribute, lowerRule), lowerRule, attribute};

        Set<String> originalMessageKeys = blueprint.keySet();

        String[] messageKeys = originalMessageKeys.toArray(new String[originalMessageKeys.size()]);

        for (String search : searches) {

            for (String messageKey : messageKeys) {

                if (messageKey.contains("*")) {

                    String pattern = Str.regexQuote(messageKey, "#");

                    pattern = pattern.replace("\\*", "([^.]*)");

                    Pattern patternMatch = Pattern.compile("^" + pattern + "\\z");

                    if (patternMatch.matcher(search).matches()) {
                        return blueprint.isCallback(messageKey, lowerRule)
                                ? blueprint.get(messageKey, Blueprint.class).get(lowerRule, String.class)
                                : blueprint.get(messageKey, String.class);
                    }

                    continue;
                }

                if (Str.is(messageKey, search)) {
                    return blueprint.isCallback(messageKey, lowerRule)
                            ? blueprint.get(messageKey, Blueprint.class).get(lowerRule, String.class)
                            : blueprint.get(messageKey, String.class);
                }

            }

        }

        return null;
    }

}
