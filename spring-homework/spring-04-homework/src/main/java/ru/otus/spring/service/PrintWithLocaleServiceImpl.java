package ru.otus.spring.service;

import org.springframework.stereotype.Service;

@Service
public class PrintWithLocaleServiceImpl implements PrintWithLocaleService {

    private final LocaleService localeService;
    private final IOService ioService;

    public PrintWithLocaleServiceImpl(LocaleService localeService, IOService ioService) {
        this.localeService = localeService;
        this.ioService = ioService;
    }

    @Override
    public void printLocalised(String messageKey, Object...args) {
        ioService.print(localeService.translateMessage(messageKey, args));
    }
}
