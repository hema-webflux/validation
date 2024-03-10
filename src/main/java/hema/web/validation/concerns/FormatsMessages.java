package hema.web.validation.concerns;

import hema.web.validation.message.Str;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public interface FormatsMessages {

    default void test() {

        Map<String, String> sources = new HashMap<>();

        sources.put("name", ":Attribute 不能为空");

        String value = getFromLocalArray("name", "required", sources);
    }

    String replacePlaceholderInString(String attribute);

    default String getFromLocalArray(String attribute, String lowerRule, @Nullable Map<String, ?> source) {

        source = source != null ? source : customMessage();

        String[] keys = {String.format("%s.%s", attribute, lowerRule), lowerRule, attribute};

        Set<String> originalMessageKeys = source.keySet();

        String[] messageKeys = originalMessageKeys.toArray(new String[originalMessageKeys.size()]);

        for (String key : keys) {

            for (String messageKey : messageKeys) {

                if (messageKey.contains("*")) {

                    String pattern = Str.regexQuote(messageKey, "#");
                    pattern = pattern.replace("\\*", "([^.]*)");

                    Pattern patternMatch = Pattern.compile("^" + pattern + "\\z");

                    if (patternMatch.matcher(key).matches()) {

                        Object message = source.get(messageKey);

                        if (message instanceof Map<?, ?> && ((Map<?, ?>) message).containsKey(lowerRule)) {
                            return (String) ((Map<?, ?>) message).get(lowerRule);
                        }

                        return (String) message;
                    }

                    continue;
                }

                if (Str.is(messageKey, key)) {

                    Object message = source.get(messageKey);

                    if (messageKey.equals(attribute) && message instanceof Map<?, ?> && ((Map<?, ?>) message).containsKey(lowerRule)) {
                        return (String) ((Map<?, ?>) message).get(lowerRule);
                    }

                    return (String) message;
                }

            }

        }

        return null;
    }

    Map<String, Object> customMessage();

}
