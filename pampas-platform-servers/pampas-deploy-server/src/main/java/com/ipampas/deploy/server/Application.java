 package com.ipampas.deploy.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.deployer.resource.maven.MavenProperties;
import org.springframework.cloud.deployer.resource.maven.MavenResource;
import org.springframework.cloud.deployer.resource.maven.MavenResourceLoader;
import org.springframework.cloud.deployer.spi.app.AppDeployer;
import org.springframework.cloud.deployer.spi.app.AppInstanceStatus;
import org.springframework.cloud.deployer.spi.app.AppStatus;
import org.springframework.cloud.deployer.spi.core.AppDefinition;
import org.springframework.cloud.deployer.spi.core.AppDeploymentRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringCloudApplication
public class Application {

    public static void main(String[] args) {
        //
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AppDeployer appDeployer) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                //
                String appName = "pampas-admin-server";
                Map<String, String> appProperties = new LinkedHashMap<>();
                // appProperties.put("server.port", "9000"); use dynamic port
                AppDefinition appDefinition = new AppDefinition(appName, appProperties);
                //
                MavenProperties mavenProperties = new MavenProperties();
                MavenResourceLoader mavenResourceLoader = new MavenResourceLoader(mavenProperties);
                Resource appResource = mavenResourceLoader.getResource(MavenResource.URI_SCHEME + "://com.ipampas:pampas-config-server:1.0.0-SNAPSHOT");
                //
                Map<String, String> deploymentProperties = new LinkedHashMap<>();
                deploymentProperties.put("spring.cloud.deployer.group", "pampas");
                AppDeploymentRequest appDeploymentRequest = new AppDeploymentRequest(appDefinition, appResource, deploymentProperties);
                //
                String deployId = appDeployer.deploy(appDeploymentRequest);
                //
            }
        };
    }

}
