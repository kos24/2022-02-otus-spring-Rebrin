package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.IOServiceConsole;

@Configuration
public class ServicesConfig {

    @Bean
    public IOService ioService() {
        return new IOServiceConsole(System.out, System.in);
    }

}
