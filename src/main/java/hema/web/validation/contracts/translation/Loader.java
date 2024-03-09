package hema.web.validation.contracts.translation;

import java.io.IOException;

public interface Loader {

    void load(String locale, String namespace) throws IOException;

}
