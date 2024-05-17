package hema.web.validation.concerns;

import hema.web.validation.message.Str;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ValidationData {

    static Map<String, Object> initializeAndGatherData(String attribute, Map<String, Object> masterData) {

        Map<String, Object> data = flattenMap(initializeAttributeOnData(attribute, masterData));

    }

    private static Map<String, Object> flattenMap(Map<String, Object> map) {
        return map.entrySet().stream()
                .flatMap(entry -> flattenEntry(entry).entrySet().stream())
                .collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> flattenEntry(Map.Entry<String, Object> entry) {
        String key   = entry.getKey();
        Object value = entry.getValue();

        if (value instanceof Map) {
            Map<String, Object> nestedMap = (Map<String, Object>) value;
            return flattenMap(nestedMap).entrySet().stream()
                    .collect(HashMap::new, (m, e) -> m.put(key + "." + e.getKey(), e.getValue()), HashMap::putAll);
        }

        Map<String, Object> flattenedEntry = new HashMap<>();
        flattenedEntry.put(key, value);
        return flattenedEntry;
    }

    static Map<String, Object> initializeAttributeOnData(String attribute, Map<String, Object> masterData) {

        String explicitPath = getLeadingExplicitAttributePath(attribute);

        Map<String, Object> data = extractDataFromPath(explicitPath, masterData);

        if (!attribute.contains("\\*") || attribute.endsWith("*")) {
            return data;
        }

        data.put(attribute, null);

        return data;
    }

    private static Map<String, Object> extractValuesForWildcards(Map<String, Object> masterData, Map<String, Object> data, String attribute) {

        Set<String> keys = new HashSet<>();

        String regex = Str.regexQuote(attribute, "#").replace("\\*", "[^\\.]+");

        Pattern pattern = Pattern.compile(regex);

        data.keySet().forEach(key -> {

            Matcher matcher = pattern.matcher(key);

            if (matcher.find()) {
                keys.add(matcher.group(0));
            }

        });

        Map<String, Object> result = new HashMap<>();

        keys.forEach(key -> {
            result.put(key, masterData.get(key));
        });

        return result;
    }

    static Map<String, Object> extractDataFromPath(String attribute, Map<String, Object> masterData) {

        Map<String, Object> results = new HashMap<>();

        Object value = masterData.getOrDefault(attribute, "__missing__");

        if (value.getClass().equals(String.class) && value.equals("__missing__")) {
            results.put(attribute, value);
        }

        return results;
    }

    static String getLeadingExplicitAttributePath(String attribute) {

        String[] attributes = attribute.split("\\*");

        return attributes[0].replaceAll("\\.", "");
    }

}
