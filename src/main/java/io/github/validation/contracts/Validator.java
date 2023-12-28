package io.github.validation.contracts;

import io.github.validation.support.MessageBag;

import java.util.Map;

public interface Validator {

    Map<String, Object> validate();

    Map<String, Object> validated();

   <T> Validator merge(T extras);

    MessageBag errors();

    default boolean empty(String field){
        return has(field) && validate().get(field) != null;
    }

    default boolean has(String field) {
        return validate().containsKey(field);
    }

}
