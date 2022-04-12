package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.GenreService;

import java.util.Arrays;

@ShellComponent
@RequiredArgsConstructor
public class AppShellStarter {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @ShellMethod(value = "Start", key = {"s", "start"})
    public void start() {
        System.out.println("Before insert new book print all available authors (command - la) and genres (command - lg)");
        System.out.printf("In order to insert new book please use command \"insert\" with the following format:%n");
        System.out.printf("Book title, book genre, \"book author, [book author]\"  %n");
    }

    @ShellMethod(value = "list AllInfo", key = {"l", "list"})
    public void list() {
        bookService.printAllWithInfo();
    }

    @ShellMethod(value = "list authors", key = {"la", "listAuthors"})
    public void listAuthors() {
        authorService.printAll();
    }

    @ShellMethod(value = "list genres", key = {"lg", "listGenres"})
    public void listGenres() {
        genreService.printAll();
    }

    @ShellMethod(value = "insert book", key = {"i", "insert"})
    public void insert(@ShellOption String title, @ShellOption String genre, @ShellOption String args) {
        String[] authors = Arrays.stream(args.split(",")).map(String::trim).toArray(String[]::new);
        bookService.insert(title, genre, authors);
    }

    @ShellMethod(value = "get book by title", key = {"b", "book"})
    public void getBookByTitle(@ShellOption String title) {
        System.out.println(bookService.getBookByTitle(title));
    }

    @ShellMethod(value = "get book by id", key = {"bi", "bookId"})
    public void getBookByTitle(@ShellOption Long id) {
        System.out.println(bookService.getBookById(id));
    }

    @ShellMethod(value = "delete book by id", key = {"d", "delete"})
    public void deleteById(@ShellOption Long id) {
        bookService.deleteBookById(id);
    }
}
