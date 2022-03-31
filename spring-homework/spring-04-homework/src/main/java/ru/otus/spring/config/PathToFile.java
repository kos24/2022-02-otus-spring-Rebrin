package ru.otus.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(prefix = "path")
@Component
public class PathToFile {

    private Map<String, String> pathToFileMap;

    public Map<String, String> getPathToFileMap() {
        return pathToFileMap;
    }

    public void setPathToFileMap(Map<String, String> pathToFileMap) {
        this.pathToFileMap = pathToFileMap;
    }
}