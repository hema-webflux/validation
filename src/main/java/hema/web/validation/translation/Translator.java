package hema.web.validation.translation;

import hema.web.validation.contracts.translation.Loader;

import java.util.Map;

public class Translator extends NamespacedItemResolver implements hema.web.validation.contracts.translation.Translator {

    private final Loader loader;

    private final String locale;

    Translator(Loader loader, String locale) {
        this.loader = loader;
        this.locale = locale;
    }

    @Override
    public String get(String key, Map<String, Object> replace, String locale) {
        return null;
    }

    @Override
    public String choice(String key) {
        return null;
    }

    @Override
    public String getLocale() {
        return null;
    }

    @Override
    public void setLocale(String locale) {

    }
}
