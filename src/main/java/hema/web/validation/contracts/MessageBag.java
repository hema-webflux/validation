package hema.web.validation.contracts;

import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.Set;

public interface MessageBag {

    String first(@Nullable String name);

    Set<String> get(@Nullable String name);

    MessageBag add(String name, String message);

    boolean has(@Nullable String name);

    Map<String, Set<String>> messages();

    boolean isEmpty();
}
