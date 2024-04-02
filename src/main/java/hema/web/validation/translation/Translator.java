package hema.web.validation.translation;

import hema.web.validation.contracts.translation.Loader;
import hema.web.validation.contracts.translation.Translation;

import java.util.Map;

class Translator extends NamespacedItemResolver implements Translation {

    private final Loader loader;

    private String locale;

    Translator(Loader loader, String locale, Map<String, String[]> parsed) {
        super(parsed);
        this.loader = loader;
        this.locale = locale;
    }

    @Override
    public String get(String key, Map<String, Object> replace, String locale) {
        return null;
    }

    private String[] parse(String key) {

        String[] segments = parseKey(key);

        if (segments[0] == null) {
            segments[0] = "*";
        }

        return segments;
    }

    @Override
    public String choice(String key) {
        return null;
    }

    @Override
    public String getLocale() {
        return locale;
    }

    @Override
    public void setLocale(String locale) {
        this.locale = locale;
    }
}
