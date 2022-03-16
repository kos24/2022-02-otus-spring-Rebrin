package ru.otus.spring.service;

import ru.otus.spring.exception.QuestionLoadingException;

public class DemoService {

    private DemoService() {
    }

    public static void run(TestService testService) {
        try {
            testService.doTest();

        } catch (QuestionLoadingException ex) {
            System.out.println("QuestionLoadingException is thrown");
        }
    }
}