package io.github.validation.contracts.rule;

public interface Blueprint {

    Blueprint field(String name);

    default void test(){

    }

}
