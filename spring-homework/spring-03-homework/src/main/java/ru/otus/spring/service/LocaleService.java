package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleService {

    @Value("${lang.locale:en-EN}")
    private String region;
    private final MessageSource messageSource;

    public LocaleService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getRegion() {
        return region;
    }

    public String translateMessage(String property, String...args) {
        return messageSource.getMessage(property, args, Locale.forLanguageTag(region));
    }
}
