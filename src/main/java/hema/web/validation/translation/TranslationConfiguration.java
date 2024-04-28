package hema.web.validation.translation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.util.HashMap;

@Configuration
public class TranslationConfiguration {

    private final ApplicationContext context;

    public TranslationConfiguration(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    @Lazy
    public Translation translator() {
        return new Translator(loader(), "en", new HashMap<>());
    }

    @Bean
    @Lazy
    public Loader loader() {
        return new FileLoader(
                context.getBean(FileSystemResourceLoader.class),
                new String[]{
                        STR."\{ResourceLoader.CLASSPATH_URL_PREFIX}lang",

                }
        );
    }

}
