package hema.web.validation.contracts.translation;

import java.util.Map;

public interface Loader {

    Map<String, Object> load(String locale);

}
