package hema.web.validation.concerns.translation;

import org.springframework.lang.Nullable;

import java.util.Map;

public interface Translation {

    String get(String key, @Nullable String locale);

    String choice(String key);

    String getLocale();

    void setLocale(String locale);
}
