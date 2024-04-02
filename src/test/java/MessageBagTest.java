import hema.web.validation.concerns.ValidatorConfiguration;
import hema.web.validation.contracts.MessageBag;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.GenericApplicationContext;

public class MessageBagTest {

    private static MessageBag messageBag;

    @BeforeAll
    public static void makeMessageBag() {
        messageBag = new ValidatorConfiguration(new GenericApplicationContext()).messageBag();
    }

    @Test
    public void testHas() {
        add();
        assertTrue(messageBag.has("name"));
    }

    private void add() {
        messageBag.add("name", "Must be required.")
                .add("name", "Must be string type.");
    }

    @Test
    public void testMessagesSize() {
        add();
        assertEquals(messageBag.get("name").size(), 2);
    }

    @Test
    public void testFirst() {
        add();
        assertEquals(messageBag.first("name"), "Must be required.");
    }

    @Test
    public void testMessage() {
        assertNotEquals(messageBag, null);
    }
}
