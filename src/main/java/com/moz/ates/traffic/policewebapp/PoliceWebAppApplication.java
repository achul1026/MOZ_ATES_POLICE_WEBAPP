package com.moz.ates.traffic.policewebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.moz.ates.traffic.policewebapp","com.moz.ates.traffic.common"})
public class PoliceWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoliceWebAppApplication.class, args);
    }

}
