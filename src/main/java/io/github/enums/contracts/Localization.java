package io.github.enums.contracts;

import io.github.enums.exception.EnumException;
import io.github.enums.exception.EnumInheritException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public interface Localization {

    default Set<Map<String, Object>> toLocal() throws EnumException, EnumInheritException {

        Class<? extends Localization> reflectionClass = getClass();

        checkImplementation(reflectionClass);

        return mapToLocalization(reflectionClass);
    }

    private Set<Map<String, Object>> mapToLocalization(Class<? extends Localization> reflectionClass) {
        return Arrays.stream(reflectionClass.getEnumConstants()).map(element -> {

            Map<String, Object> map = new HashMap<>();

            map.put("description", callMethod(element, "description"));

            map.put("value", callMethod(element, "value"));

            return map;
        }).collect(Collectors.toSet());
    }

    private Object callMethod(Localization enumElement, String methodName) {

        Object value;

        try {
            Method method = enumElement.getClass().getMethod(methodName);

            value = method.invoke(enumElement);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(String.format("The call method [ %s ] is incorrect.", methodName));
        }
        return value;
    }

    private void checkImplementation(Class<?> reflectionClass) throws EnumInheritException, EnumException {

        if (!reflectionClass.isEnum()) {
            failedEnumerable(String.format("The implementation class [ %s ] must be an enum.", reflectionClass));
        }

        Class<?> abstractType = Enumerable.class;

        if (!abstractType.isAssignableFrom(reflectionClass)) {
            failedInherit(abstractType, reflectionClass);
        }

        abstractType = Descriptor.class;

        if (!abstractType.isAssignableFrom(reflectionClass)) {
            failedInherit(abstractType, reflectionClass);
        }

    }

    private void failedInherit(Class<?> type, Class<?> reflection) throws EnumInheritException {
        throw new EnumInheritException(String.format("Enum [ %s ] must implement [ %s ].", reflection.getSimpleName(), type.getSimpleName()));
    }

    private void failedEnumerable(String message) throws EnumException {
        throw new EnumException(message);
    }
}
