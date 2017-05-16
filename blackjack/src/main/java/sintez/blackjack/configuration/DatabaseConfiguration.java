package sintez.blackjack.configuration;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "sintez.blackjack.repository")
@EntityScan(basePackages = "sintez.blackjack.model")
public class DatabaseConfiguration {
}
