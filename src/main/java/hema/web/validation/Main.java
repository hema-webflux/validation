package hema.web.validation;

import hema.web.validation.concerns.FormatsMessages;
import hema.web.validation.contracts.Blueprint;
import hema.web.validation.contracts.BlueprintClosure;
import hema.web.validation.message.Str;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        System.out.println(Str.random(16));

        String value = "email*";

        value = Str.regexQuote(value, "#");

        value = value.replace("\\*", "([^.]*)");

        System.out.println(Pattern.compile("^" + value + "\\z").matcher("email_verify").find());

        FormatsMessages formatsMessages = new Formats();

        String v = formatsMessages.getFromLocalArray("password_confirm", "max", null);

        System.out.println(v);

        Blueprint blueprint = new TestBlueprint();

        blueprint = blueprint.bind("name.required", ":attribute 不能为空")
                .bind("name.string", ":attribute 必须为字符串")
                .bind("name.unique", ":attribute 只能是唯一的")
                .bind("email*", blueprintClosure -> (
                        blueprintClosure.bind("required", ":attribute 不能为空")
                                .bind("email", ":attribute 必须为邮箱格式")
                ));
    }

    static class TestBlueprint implements Blueprint, Cloneable {

        private final Map<String, Object> store = new HashMap<>();

        @Override
        public Blueprint bind(String attribute, String value) {

            store.put(attribute, value);

            return this;
        }

        @Override
        public Blueprint bind(String attribute, BlueprintClosure closure) {

            store.put(attribute, closure.closure(this.clone()));

            return this;
        }

        @Override
        public boolean isClosure(String attribute) {
            return store.get(attribute) instanceof Blueprint;
        }

        @Override
        public boolean has(String key) {
            return false;
        }

        @Override
        public TestBlueprint clone() {
            try {
                TestBlueprint clone = (TestBlueprint) super.clone();
                clone.store.clear();
                // TODO: copy mutable state here, so the clone can't change the internals of the original
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    static class Formats implements FormatsMessages {

        @Override
        public String replacePlaceholderInString(String attribute) {
            return null;
        }

        @Override
        public Map<String, Object> customMessage() {
            Map<String, Object> messages = new HashMap<>();

            messages.put("name.required", ":attribute 不能为空");

            Map<String, Object> passwordMessage = new HashMap<>();
            passwordMessage.put("required", ":attribute 不能为空");
            passwordMessage.put("max", "attribute 最大为 :max");

            messages.put("password*", passwordMessage);

            return messages;
        }
    }
}