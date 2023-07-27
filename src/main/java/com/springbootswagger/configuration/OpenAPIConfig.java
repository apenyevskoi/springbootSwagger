package com.springbootswagger.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger configuration class
 * Two API servers: dev and prod
 * License: GNU
 * Server: http://localhost:8080/swagger-ui/index.html
 */
@Configuration
public class OpenAPIConfig {

    @Value("${someValue.using.dots.dev-url}")
    private String devUrl;

    @Value("${someValue.using.dots.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI(){
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("someemail@mail.mail");
        contact.setName("Server's admin name");
        contact.setUrl("someurl.url");

        License gnuLicense = new License().name("GNU License").url("https://www.gnu.org/licenses/gpl-3.0.html");

        Info info = new Info()
                .title("Tutorial Management API")
                .version("1.1.1")
                .contact(contact)
                .description("API descibes endpoints to manage tutorial").termsOfService("url-to-terms-conditions.url")
                .license(gnuLicense);
        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}