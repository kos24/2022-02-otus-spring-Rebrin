package ru.otus.spring.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.service.DemoService;
import ru.otus.spring.service.PrintWithLocaleService;

@ShellComponent
public class AppShellStarter {

    private final PrintWithLocaleService printer;
    private final DemoService demoService;
    private boolean introDisplayed;

    public AppShellStarter(PrintWithLocaleService printer, DemoService demoService) {
        this.printer = printer;
        this.demoService = demoService;
    }

    @ShellMethod(value = "intro", key = {"i", "intro"})
    public void intro() {
        printer.printLocalised("lang.hello");
        introDisplayed = true;
    }

    @ShellMethod(value = "Start test", key = {"s", "start"})
    @ShellMethodAvailability(value = "isStartAvailable")
    public void start() {
        demoService.run();
    }

    private Availability isStartAvailable() {
        return !introDisplayed ? Availability.unavailable("Сначала посмотрите вступительную часть - \"intro\"") : Availability.available();
    }
}
