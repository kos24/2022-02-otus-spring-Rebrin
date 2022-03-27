package ru.otus.spring.service;

public interface LocaleService {
    String translateMessage(String property, Object...args);
}
