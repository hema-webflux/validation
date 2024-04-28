package hema.web.validation.translation;

import java.util.Map;

public interface Loader {

    Map<String, Object> load(String locale);

}
