package io.karmanov.challenge;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.karmanov.challenge.services.MessageProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
public class ChallengeApplication implements CommandLineRunner {

    @Autowired
    private MessageProcessorService messageProcessorService;

    public static void main(String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        messageProcessorService.processMessages();
    }
}
