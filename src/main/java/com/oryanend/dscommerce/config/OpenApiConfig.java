package com.oryanend.dscommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DSCommerce")
                        .version("v1")
                        .description("""
                            O DSCommerce é uma API poderosa para e-commerce, com endpoints completos para cadastro, 
                            gerenciamento e integração de todos os recursos necessários em uma loja virtual.
                            
                            **Contatos:**
                            - [GitHub](https://github.com/oryanend)
                            - [LinkedIn](https://www.linkedin.com/in/oryanend/)
                            - [Portifólio](https://oryanend.github.io)
                            """));
    }
}
