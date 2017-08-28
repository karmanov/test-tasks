package io.karmanov.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TransactionsMgmtServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsMgmtServiceApplication.class, args);
    }
}
