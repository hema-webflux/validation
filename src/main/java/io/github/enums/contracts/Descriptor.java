package io.github.enums.contracts;

import io.github.enums.annotations.Description;

public interface Descriptor {
    default String description() {

        Class<? extends Descriptor> reflector = getClass();

        System.out.println(reflector.getSimpleName());
        System.out.println(reflector.getCanonicalName());

        System.out.println(reflector.getAnnotation(Description.class));

        return "";
    }
}
