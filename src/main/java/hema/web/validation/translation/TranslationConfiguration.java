package hema.web.validation.translation;

import hema.web.validation.contracts.translation.Loader;
import hema.web.validation.contracts.translation.Translation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class TranslationConfiguration {

    private final ApplicationContext applicationContext;

    public TranslationConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    @Lazy
    public Translation translator() {
        return new Translator(loader(), "en");
    }

    @Bean
    @Lazy
    public Loader loader() {
        return new FileLoader(applicationContext.getBean(FileSystemResourceLoader.class), new String[]{
                String.format("%s%s", ResourceLoader.CLASSPATH_URL_PREFIX, "lang")
        });
    }

}
