package hema.web.validation.concerns;

import hema.web.validation.contracts.MessageBag;
import hema.web.validation.message.Str;
import org.springframework.lang.Nullable;

import java.util.*;

record ValidateMessageBag(Map<String, Set<String>> messages) implements MessageBag {

    @Override
    public boolean has(@Nullable String key) {

        if (isEmpty()) {
            return false;
        }

        return !first(key).isEmpty();
    }

    @Override
    public String first(String name) {

        if (name == null) {

            Optional<Set<String>> optional = messages.values().stream().findFirst();

            if (optional.isPresent()) {
                return optional.stream().findFirst().get().toString();
            }
        }

        return get(name).stream().findFirst().orElse("");
    }

    @Override
    public Set<String> get(String name) {

        if (name == null) {
            return new HashSet<>();
        }

        if (messages.containsKey(name)) {
            return messages.get(name);
        }

        return name.contains("*") ? getMessagesForWildcardKey(name) : new HashSet<>();
    }

    private Set<String> getMessagesForWildcardKey(String key) {
        return messages.entrySet()
                .stream()
                .filter(predicate -> Str.is(key, predicate.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(new HashSet<>());
    }

    @Override
    public MessageBag add(String key, String value) {

        if (isUnique(key, value)) {

            Set<String> messages = this.messages.get(key);

            if (messages == null) {
                messages = new HashSet<>();
            }

            messages.add(value);

            this.messages.put(key, messages);
        }

        return this;
    }

    private boolean isUnique(String key, String message) {
        return !messages.containsKey(key) || !messages.get(key).contains(message);
    }

    @Override
    public boolean isEmpty() {
        return messages.isEmpty();
    }
}
