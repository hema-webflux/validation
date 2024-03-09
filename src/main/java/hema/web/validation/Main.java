package hema.web.validation;

import hema.web.validation.concerns.FormatsMessages;
import hema.web.validation.message.Str;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        String value = "email*";

        value = Str.regexQuote(value,"#");

        value = value.replace("\\*", "([^.]*)");

        System.out.println(Pattern.compile("^" + value + "\\z").matcher("email_verify").find());

        FormatsMessages formatsMessages = new Formats();

       String v = formatsMessages.getFromLocalArray("password_confirm","max",null);

       System.out.println(v);
    }

    static class Formats implements FormatsMessages {

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