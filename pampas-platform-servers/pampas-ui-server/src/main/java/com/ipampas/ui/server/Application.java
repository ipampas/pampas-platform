package com.ipampas.ui.server;


import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.rmi.server.RemoteServer;
import java.util.Arrays;

@EnableOAuth2Sso
//@EnableResourceServer
@SpringCloudApplication
public class Application {

    public static void main(String[] args) {
        //
        SpringApplication.run(Application.class, args);
    }

    @Profile("debug")
    @Bean
    RequestDumperFilter requestDumperFilter() {
        return new RequestDumperFilter();
    }

    @Bean
    BeanPostProcessor remoteTokenServicesProcessor() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Arrays.asList(new AcceptJsonRequestInterceptor()));
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
                if(bean instanceof RemoteTokenServices) {
                    //
                    ((RemoteTokenServices) bean).setRestTemplate(restTemplate);
                }
                return bean;
            }
        };
    }

    static class AcceptJsonRequestInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            return execution.execute(request, body);
        }

    }

    @Bean
    RestOperations restOperations(OAuth2ProtectedResourceDetails details,
                                  OAuth2ClientContext oauth2ClientContext) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }

}
