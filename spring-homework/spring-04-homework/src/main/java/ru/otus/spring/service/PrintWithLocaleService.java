package ru.otus.spring.service;

public interface PrintWithLocaleService {

    void printLocalised(String messageKey, Object...args);
}
