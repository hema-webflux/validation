package hema.web.validation.concerns.translation;

import hema.web.validation.message.Str;

import java.util.Arrays;
import java.util.Map;

class NamespacedItemResolver {

    private final Map<String, String[]> parsed;

    NamespacedItemResolver(Map<String, String[]> parsed) {
        this.parsed = parsed;
    }

    public final String[] parseKey(String key) {

        if (this.parsed.containsKey(key)) {
            return this.parsed.get(key);
        }

        String[] parsed = parseBasicSegments(key.split("\\."));

        this.parsed.put(key, parsed);

        return parsed;
    }

    private String[] parseBasicSegments(String[] segments) {

        String group = segments[0];

        String item = null;

        if (segments.length == 1) {
            item = Str.implode(".", Arrays.copyOfRange(segments, 1, segments.length - 1));
        }

        return new String[]{null, group, item};
    }

    protected final void flushParsedKeys() {
        parsed.clear();
    }

}
