package io.github.enums;

import io.github.enums.annotations.Description;
import io.github.enums.contracts.Descriptor;
import io.github.enums.contracts.Enumerable;
import io.github.enums.contracts.Localization;

public enum Color implements Descriptor, Localization, Enumerable<String> {
    @Description("红色")
    Red("red"),

    @Description("蓝色")
    Blue("blue");

    private final String value;

    Color(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
