import hema.web.validation.message.Str;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilTest {

    @Test
    public void testIsMethod() {
        Assertions.assertTrue(Str.is("na*", "name"));
    }
}
