package hema.web.validation;

import hema.web.validation.contracts.Factory;

public class Main {

    public static void main(String[] args) {

        Factory factory = null;

        factory.extend("phone", (attribute, value, parameters) -> {

            Object val = parameters.get("xx");

            return value.equals(val);
        }, "xxx");
    }

}