import hema.web.validation.message.Str;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilTests {

    @Test
    public void testIsMethod() {
        Assertions.assertTrue(Str.is("na*", "name"));
    }
}
