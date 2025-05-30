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
        "org.example.handanddomain.domain.*.infra.jpa"
})
@EntityScan(basePackages = {
        "org.example.handanddomain.domain.*.infra.jpa"
})
public class HandandDomainConfiguration {
}
