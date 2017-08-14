package com.ipampas.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringCloudApplication
public class Application {

    public static void main(String[] args) {
        //
        SpringApplication.run(Application.class, args);
    }

}
