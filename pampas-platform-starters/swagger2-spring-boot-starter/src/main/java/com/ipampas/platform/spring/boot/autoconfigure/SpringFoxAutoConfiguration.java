package com.ipampas.platform.spring.boot.autoconfigure;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@ConditionalOnClass(Docket.class)
@ConditionalOnProperty(SpringFoxProperties.PREFIX + ".enabled")
@EnableConfigurationProperties(SpringFoxProperties.class)
public class SpringFoxAutoConfiguration {

    @Autowired
    private SpringFoxProperties springFoxProperties;

    @Configuration
    @EnableSwagger2
    class SpringFoxConfiguration {}

    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(springFoxProperties.getGroupName())
                .select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                    .build()
                .apiInfo(apiInfo())
                .host(springFoxProperties.getHost());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                springFoxProperties.getApiInfo().getTitle(),
                springFoxProperties.getApiInfo().getDescription(),
                springFoxProperties.getApiInfo().getVersion(),
                springFoxProperties.getApiInfo().getTermsOfServiceUrl(),
                new Contact(
                        springFoxProperties.getApiInfo().getContactName(),
                        springFoxProperties.getApiInfo().getContactUrl(),
                        springFoxProperties.getApiInfo().getContactEmail()
                ),
                springFoxProperties.getApiInfo().getLicense(),
                springFoxProperties.getApiInfo().getLicenseUrl(),
                Collections.emptyList()
        );
    }

}
