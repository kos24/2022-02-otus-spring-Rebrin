package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.service.DemoService;

@SpringBootApplication
public class Spring03HomeworkApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Spring03HomeworkApplication.class, args);

		DemoService demoService = context.getBean(DemoService.class);
		demoService.run();
	}

}
