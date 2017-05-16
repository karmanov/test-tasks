package sintez.blackjack.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("sintez.blackjack.*")
public class ApplicationConfiguration {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApplicationConfiguration.class);
        application.setShowBanner(false);
        application.run();
    }
}
