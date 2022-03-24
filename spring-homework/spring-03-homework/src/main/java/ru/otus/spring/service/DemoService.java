package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.exception.QuestionLoadingException;

@Service
public class DemoService {

    private final TestService testService;
    private final IOService ioService;
    private final LocaleService localeService;

    public DemoService(TestService testService, IOService ioService, LocaleService localeService) {
        this.testService = testService;
        this.ioService = ioService;
        this.localeService = localeService;
    }

    public void run() {
        try {
            testService.doTest();
        } catch (QuestionLoadingException ex) {
            ioService.print(localeService.translateMessage("lang.error"));
        }
    }
}