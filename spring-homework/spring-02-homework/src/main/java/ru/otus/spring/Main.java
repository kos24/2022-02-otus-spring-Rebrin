package ru.otus.spring;

import org.springframework.context.annotation.*;
import ru.otus.spring.service.DemoService;

@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        DemoService demoService = context.getBean(DemoService.class);
        demoService.run();
    }
}