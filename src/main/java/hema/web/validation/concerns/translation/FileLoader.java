package hema.web.validation.concerns.translation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

final class FileLoader implements Loader {

    private final ResourceLoader resourceLoader;

    private final String[] path;

    public FileLoader(ResourceLoader resourceLoader, String[] path) {
        this.path = path;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Map<String, Object> load(String locale) {
        return loadJsonPaths(locale);
    }

    private Map<String, Object> loadPaths() {
        Map<String, Object> map = new HashMap<>();

        return null;
    }

    /**
     * Load a locale form the give JSON file path.
     *
     * @param locale String
     * @return Map
     */
    private Map<String, Object> loadJsonPaths(String locale) {
        return Arrays.stream(path).reduce(new HashMap<>(),
                (output, path) -> (HashMap<String, Object>) handleLoadJsonPaths(path, locale, output),
                (before, after) -> {
                    before.putAll(after);
                    return before;
                });
    }

    private Map<String, Object> handleLoadJsonPaths(String path, String locale, Map<String, Object> output) {

        String full = STR."\{path}/\{locale}.json";

        Resource resource = resourceLoader.getResource(full);

        if (resource.exists() && resource.isFile()) {
            return tryResolve(full, resource, output);
        }

        return output;
    }

    private Map<String, Object> tryResolve(String full, Resource resource, Map<String, Object> output) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            output = mapper.readValue(resource.getURL(), new TypeReference<>() {
            });

            if (Objects.isNull(output)) {
                fail(String.format("Translation file [%s] contains an invalid JSON structure.", full));
            }

        } catch (IOException e) {
            fail(e.getMessage());
        }

        return output;
    }

    private void fail(String message) {
        throw new RuntimeException(message);
    }
}
