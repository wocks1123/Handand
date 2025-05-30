package org.example.handanddomain;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {
        "com.example.handanddomain.*"
})
@EntityScan(basePackages = {
        "com.example.handanddomain.*"
})
public class HandandDomainConfiguration {
}
