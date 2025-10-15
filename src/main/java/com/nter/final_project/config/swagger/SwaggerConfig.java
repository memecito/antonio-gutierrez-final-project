package com.nter.final_project.config.swagger;

import org.mapstruct.BeanMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nter.final_project"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(informacion());
    }

    @Bean
    public ApiInfo informacion() {
        return new ApiInfoBuilder()
                .title("Proyecto Final")
                .description("API RESTfull que gestiona usuarios, paises, productos y pedidos")
                .version("1.0")
                .contact(new Contact("Antonio Gutíerrez",
                        "https://github.com/memecito",
                        "antonio.gutierrez@nter.es"))
                .build();
    }
}
