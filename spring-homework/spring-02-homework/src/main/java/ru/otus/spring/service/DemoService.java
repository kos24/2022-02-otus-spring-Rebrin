package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.exception.QuestionLoadingException;

@Service
public class DemoService {

    private final TestService testService;
    private final IOService ioService;

    public DemoService(TestService testService, IOService ioService) {
        this.testService = testService;
        this.ioService = ioService;
    }

    public void run() {
        try {
            testService.doTest();
        } catch (QuestionLoadingException ex) {
            ioService.print("Во время выполнения программы произошла ошибка.");
        }
    }
}