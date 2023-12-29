package io.github.validation.contracts.closure;

@FunctionalInterface
public interface FilterClosure {
    <T> T filter(T item);
}
