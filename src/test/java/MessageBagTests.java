import hema.web.validation.contracts.MessageBag;
import hema.web.validation.message.ValidateMessageBag;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MessageBagTests {

    private static final MessageBag messageBag = new ValidateMessageBag();

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
        assertEquals(messageBag.get("name").size(),2);
    }

    @Test
    public void testFirst() {
        add();
        assertEquals(messageBag.first("name"),"Must be required.");
    }

    @Test
    public void testMessage() {
        assertNotEquals(messageBag,null);
    }
}
