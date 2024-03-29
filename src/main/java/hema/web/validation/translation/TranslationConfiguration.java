package hema.web.validation.translation;

import hema.web.validation.contracts.translation.Loader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.util.HashMap;

@Configuration
public class TranslationConfiguration {

    private final ApplicationContext applicationContext;

    public TranslationConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    @Lazy
    public hema.web.validation.contracts.translation.Translator translator() {
        return new Translator(loader(), "en", new HashMap<>());
    }

    @Bean
    @Lazy
    public Loader loader() {
        return new FileLoader(
                applicationContext.getBean(FileSystemResourceLoader.class),
                new String[]{STR."\{ResourceLoader.CLASSPATH_URL_PREFIX}lang"}
        );
    }

}
