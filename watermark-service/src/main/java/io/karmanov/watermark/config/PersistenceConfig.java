package io.karmanov.watermark.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Persistence configuration
 */
@Configuration
@EnableJpaAuditing
public class PersistenceConfig {
}
