import hema.web.validation.concerns.FormatsMessages;
import hema.web.validation.concerns.store.MessageSource;
import hema.web.validation.contracts.source.SimpleSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FormatsMessageTest {

    private static final FormatsMessages formatsMessages = new Format();

    private static final SimpleSource simpleSource = new MessageSource();

    @BeforeAll
    public static void beforeAddRule() {

        simpleSource.add("name.required", "The name is required.")
                .add("email*", closure -> (
                        closure.add("email", "Must be in email format.")
                                .add("max", "Must be max length")
                ));

    }

    @Test
    public void testGetFromLocal() {

        Assertions.assertEquals(formatsMessages.getFromLocalArray("name","required",simpleSource),"The name is required.");
        Assertions.assertEquals(formatsMessages.getFromLocalArray("email_verify","email",simpleSource),"Must be in email format.");

        Assertions.assertTrue(formatsMessages.getFromLocalArray("email_confirm","max",simpleSource).contains("max length"));

        Assertions.assertTrue(formatsMessages.getFromLocalArray("avatar","url",simpleSource).isEmpty());
    }

    static class Format implements FormatsMessages {

        @Override
        public String replacePlaceholderInString(String attribute) {
            return null;
        }
    }

}
