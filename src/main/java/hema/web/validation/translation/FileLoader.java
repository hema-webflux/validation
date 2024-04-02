package hema.web.validation.translation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hema.web.validation.contracts.translation.Loader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class FileLoader implements Loader {

    private final ResourceLoader resourceLoader;

    private final String[] path;

    public FileLoader(ResourceLoader resourceLoader, String[] path) {
        this.resourceLoader = resourceLoader;
        this.path = path;
    }

    @Override
    public Map<String, Object> load(String locale) {
        return loadJsonPaths(locale);
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

            ObjectMapper jsonMapper = new ObjectMapper();

            try {
                output = jsonMapper.readValue(resource.getURL(), new TypeReference<>() {
                });

                if (Objects.isNull(output)) {
                    throw new RuntimeException(String.format("Translation file [%s] contains an invalid JSON structure.", full));
                }

                return output;
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        }

        return output;
    }
}
