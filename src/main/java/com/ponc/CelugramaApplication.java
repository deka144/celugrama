package com.ponc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling; // nuevo import

@SpringBootApplication
@EnableScheduling        // nueva anotacion: activa el scheduler en toda la app
public class CelugramaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CelugramaApplication.class, args);
    }

}
