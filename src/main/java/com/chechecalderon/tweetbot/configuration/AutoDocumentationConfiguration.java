package com.chechecalderon.tweetbot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://localhost:8080/swagger-ui.html
 */

@Configuration
@EnableSwagger2
public class AutoDocumentationConfiguration {

    @Bean
    public Docket api(@Value("${tweetbot.version}") String tweetBotVersion) {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                .basePackage("com.chechecalderon.controller"))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo(tweetBotVersion));
    }

    private ApiInfo apiInfo (String tweetBotVersion){
        ApiInfo apiInfo = new ApiInfo("Tweet Bot",
                "A very awesome Tweet Bot" +tweetBotVersion+" .",
                tweetBotVersion, "Term of service", "joseenrique86@gmail.com",
                "all rights reserved", "chechecalderon.com");

        return apiInfo;
    }


}
