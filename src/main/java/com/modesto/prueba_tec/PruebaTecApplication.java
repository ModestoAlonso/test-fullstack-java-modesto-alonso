package com.modesto.prueba_tec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.modesto.config",
        "com.modesto.prueba_tec"})
public class PruebaTecApplication {

    public static void main(String[] args) {
        SpringApplication.run(PruebaTecApplication.class, args);
    }

}
