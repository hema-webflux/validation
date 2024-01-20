package hema.web.validation.support;

import hema.web.validation.contracts.message.MessageBag;
import hema.web.validation.contracts.message.MessageProvider;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public record ValidateMessageBag(Map<String, Set<String>> messages) implements MessageBag, MessageProvider {

    @Override
    public MessageBag getMessageBag() {
        return this;
    }

    @Override
    public String first(String name) {
        return name == null
                ? all().stream().findFirst().get()
                : get(name).stream().findFirst().get();
    }

    @Override
    public Set<String> get(String name) {

        if (!messages.containsKey(name)) {
            return new HashSet<>();
        }

        if (name.contains("*")) {
            Optional<Map.Entry<String, Set<String>>> optional = messages.entrySet()
                    .stream()
                    .filter(message -> is(name, message.getKey()))
                    .findFirst();

            return optional.isEmpty() ? new HashSet<>() : optional.get().getValue();
        }

        return new HashSet<>();
    }

    private boolean is(String pattern, String value) {

        if (pattern.equals(value)) {
            return true;
        }

        pattern = pattern.replace("\\*", ".*");

        Pattern p = Pattern.compile("^" + pattern + "\\z");

        return p.matcher(value).find();
    }

    private Set<String> all() {
        return messages.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
    }

    @Override
    public MessageBag add(String key, String value) {

        if (!messages.containsKey(key)) {
            Set<String> bag = new HashSet<>();
            bag.add(value);
            messages.put(key, bag);
            return this;
        }

        messages.get(key).add(value);

        return this;
    }

    @Override
    public boolean has(@Nullable String key) {

        if (isEmpty()) {
            return false;
        }


        return true;
    }

    @Override
    public boolean isEmpty() {
        return messages.isEmpty();
    }
}
