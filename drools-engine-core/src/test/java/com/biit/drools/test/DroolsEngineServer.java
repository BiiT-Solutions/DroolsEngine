package com.biit.drools.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@SpringBootApplication
@ComponentScan({"com.biit.plugins", "com.biit.drools"})
@Service
public class DroolsEngineServer {

    public static void main(String[] args) {
        SpringApplication.run(DroolsEngineServer.class, args);
    }

}
