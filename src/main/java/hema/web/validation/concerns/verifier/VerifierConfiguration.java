package hema.web.validation.concerns.verifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
public class VerifierConfiguration {

    @Bean
    @Lazy
    @Scope("property")
    public DatabasePresenceVerifier databasePresenceVerifier() {
        return new DatabasePresenceVerifierBean();
    }

}
