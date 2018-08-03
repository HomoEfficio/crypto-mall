package io.homo.efficio.cryptomall.entity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CryptoMallEntityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoMallEntityApplication.class, args);
    }
}
