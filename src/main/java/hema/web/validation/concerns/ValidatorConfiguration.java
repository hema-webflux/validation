package hema.web.validation.concerns;

import hema.web.validation.contracts.Factory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.HashMap;

@Configuration
public class ValidatorConfiguration {

    private final ApplicationContext applicationContext;

    public ValidatorConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    @Lazy
    public Factory validatorFactory() {
        return new ValidatorFactory(applicationContext,new HashMap<>(),new HashMap<>());
    }

}
