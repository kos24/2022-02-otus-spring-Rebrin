package ru.otus.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(prefix = "path")
@Component
public class PathToFileConfig {

    Map<String, String> locale;

    public Map<String, String> getLocale() {
        return locale;
    }

    public void setLocale(Map<String, String> locale) {
        this.locale = locale;
    }
}