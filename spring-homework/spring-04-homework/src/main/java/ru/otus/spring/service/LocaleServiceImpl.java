package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.LocaleProvider;

import java.util.Locale;

@Service
public class LocaleServiceImpl implements LocaleService {

    private final LocaleProvider localeProvider;
    private final MessageSource messageSource;

    public LocaleServiceImpl(LocaleProvider localeProvider, MessageSource messageSource) {
        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
    }

    public String translateMessage(String property, Object...args) {
        return messageSource.getMessage(property, args, Locale.forLanguageTag(localeProvider.getLocale()));
    }
}