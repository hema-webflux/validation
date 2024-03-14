import hema.web.validation.concerns.FormatsMessages;
import hema.web.validation.concerns.haystack.MessageHaystack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

public class FormatsMessageTest {

    private static final FormatsMessages formatsMessages = new Format();

    private static final MessageHaystack haystack = new MessageHaystack(new HashMap<>(),new HashSet<>());

    @BeforeAll
    public static void beforeAddRule() {

        haystack.add("name.required", "The name is required.")
                .add("email*", closure -> (
                        closure.add("email", "Must be in email format.")
                                .add("max", "Must be max length")
                ));

    }

    @Test
    public void testGetFromLocal() {

        Assertions.assertEquals(formatsMessages.getFromLocalArray("name","required", haystack),"The name is required.");
        Assertions.assertEquals(formatsMessages.getFromLocalArray("email_verify","email", haystack),"Must be in email format.");

        Assertions.assertTrue(formatsMessages.getFromLocalArray("email_confirm","max", haystack).contains("max length"));

        Assertions.assertTrue(formatsMessages.getFromLocalArray("avatar","url", haystack).isEmpty());
    }

    static class Format implements FormatsMessages {

        @Override
        public String replacePlaceholderInString(String attribute) {
            return null;
        }
    }

}
