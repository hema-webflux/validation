package hema.web.validation.contracts.translation;

import org.springframework.lang.Nullable;

import java.util.Map;

public interface Translation {

    String get(String key, Map<String, Object> replace, @Nullable String locale);

    String choice(String key);

    String getLocale();

    void setLocale(String locale);
}
