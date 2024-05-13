import hema.web.validation.concerns.translation.Loader;
import hema.web.validation.concerns.translation.TranslationConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Map;

public class LoaderTest {

    private static Loader loader;

    @BeforeAll
    public static void beforeMakeLoader() {

        loader = new TranslationConfiguration(new GenericApplicationContext()).loader();
    }

    @Test
    public void testOutputIsNotNull() {
        Map<String, Object> output = loader.load("en");

        Assertions.assertNotEquals(output, null);
    }

    @Test
    public void testOutputExistsAttribute() {

        Map<String, Object> output = loader.load("en");

        Assertions.assertTrue(output.containsKey("accepted"));
        Assertions.assertTrue(output.containsKey("attributes"));
    }

    @Test
    public void testAttributeIsMap() {
        Map<String, Object> output = loader.load("zh_cn");

        Assertions.assertInstanceOf(Map.class,output.get("attributes"));
    }

}
