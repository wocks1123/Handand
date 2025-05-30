package org.example.handandapi;

import org.example.handanddomain.HandandDomainConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {HandandDomainConfiguration.class} )
public class HandandApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandandApiApplication.class, args);
    }

}
