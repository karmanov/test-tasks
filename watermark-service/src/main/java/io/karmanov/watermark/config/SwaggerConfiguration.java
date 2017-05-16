package io.karmanov.watermark.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${watermark.swagger.title}")
    private String title;
    @Value("${watermark.swagger.description}")
    private String description;
    @Value("${watermark.swagger.licenseUrl}")
    private String licenseUrl;
    @Value("${watermark.swagger.contactName}")
    private String contactName;
    @Value("${watermark.swagger.contactUrl}")
    private String contactUrl;
    @Value("${watermark.swagger.contactEmail}")
    private String contactEmail;
    @Value("${watermark.swagger.license}")
    private String license;
    @Value("${watermark.swagger.version}")
    private String version;


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(licenseUrl)
                .contact(new Contact(contactName, contactUrl, contactName))
                .license(license)
                .version(version)
                .build();
    }
}
