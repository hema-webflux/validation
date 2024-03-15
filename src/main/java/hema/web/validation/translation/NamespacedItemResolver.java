package hema.web.validation.translation;

import hema.web.validation.message.Str;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NamespacedItemResolver {

    private final Map<String, String[]> parsed = new HashMap<>();

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

        String item = segments.length == 1
                ? null
                : Str.implode(".", Arrays.copyOfRange(segments, 1, segments.length - 1));

        return new String[]{null, group, item};
    }

    protected final void flushParsedKeys() {
        parsed.clear();
    }

}
