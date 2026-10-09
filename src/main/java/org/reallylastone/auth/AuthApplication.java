package org.reallylastone.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"org.reallylastone.auth", "org.reallylastone.common"})
@EnableJpaRepositories(
        basePackages = {"org.reallylastone.auth", "org.reallylastone.common.user.repository",
                        "org.reallylastone.common.event.repository"})
@EntityScan(basePackages = {"org.reallylastone.auth", "org.reallylastone.common"})
@EnableScheduling
@ConfigurationPropertiesScan
public class AuthApplication {

    static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
