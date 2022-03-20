package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.IOServiceConsole;

@Configuration
@PropertySource({"classpath:app.properties"})
public class ServicesConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public IOService ioService() {
        return new IOServiceConsole(System.out, System.in);
    }
}
