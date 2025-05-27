package com.oryanend.corecommerce.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CoreCommerce")
                        .version("v2t ")
                        .description("""
                            A CoreCommerce é uma API poderosa para e-commerce, com endpoints completos para cadastro, 
                            gerenciamento e integração de todos os recursos necessários em uma loja virtual.
                            
                            **Contatos:**
                            - [GitHub](https://github.com/oryanend)
                            - [LinkedIn](https://www.linkedin.com/in/oryanend/)
                            - [Portifólio](https://oryanend.github.io)
                            
                            Baseado no DSCommerce do professor Nelio Alves.
                            """))
                .components(new Components()
                        .addParameters("page", new Parameter()
                                .name("page")
                                .in("query")
                                .description("Número da página (0-based)")
                                .schema(new IntegerSchema().example(0)))
                        .addParameters("size", new Parameter()
                                .name("size")
                                .in("query")
                                .description("Tamanho da página")
                                .schema(new IntegerSchema().example(12)))
                        .addParameters("sort", new Parameter()
                                .name("sort")
                                .in("query")
                                .description("Campo(s) de ordenação (ex: name,asc)")
                                .schema(new StringSchema().example("name"))));
    }
}
