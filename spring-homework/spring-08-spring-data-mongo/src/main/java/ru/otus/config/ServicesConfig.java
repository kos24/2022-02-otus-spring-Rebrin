package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.IOService;
import ru.otus.service.IOServiceConsole;

@Configuration
public class ServicesConfig {

    @Bean
    public IOService ioService() {
        return new IOServiceConsole(System.out, System.in);
    }

}
