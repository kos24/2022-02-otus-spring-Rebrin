package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppConfig implements FileProvider, LocaleProvider {

    private final String localeTag;
    private final PathToFile pathToFile;

    public AppConfig(@Value("${lang.locale:en-EN}") String localeTag,
                     PathToFile pathToFile) {
        this.localeTag = localeTag;
        this.pathToFile = pathToFile;
    }

    @Override
    public String getFileName() {
        return Optional.ofNullable(pathToFile.getPathToFileMap().get(localeTag))
                .orElse(pathToFile.getPathToFileMap().get("default"));
    }

    @Override
    public String getLocale() {
        return localeTag;
    }
}