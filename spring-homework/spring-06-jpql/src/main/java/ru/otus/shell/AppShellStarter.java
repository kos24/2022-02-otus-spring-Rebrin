package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.dto.BookRequestDto;
import ru.otus.dto.CommentRequestDto;
import ru.otus.service.BookService;
import ru.otus.service.CommentService;
import ru.otus.service.PrintModelService;

import java.util.Arrays;

@ShellComponent
@RequiredArgsConstructor
public class AppShellStarter {

    private final BookService bookService;
    private final PrintModelService printModelService;
    private final CommentService commentService;

    @ShellMethod(value = "Start", key = {"s", "start"})
    public void start() {
        System.out.println("Before insert new book print all available authors (command - la) and genres (command - lg)");
        System.out.printf("In order to insert new book please use command \"insert\" with the following format:%n");
        System.out.printf("Book title, book genre, \"book author, [book author]\"  %n");
    }

    @ShellMethod(value = "list AllInfo", key = {"l", "list"})
    public void list() {
        printModelService.printAllBooksWithInfo();
    }

    @ShellMethod(value = "list authors", key = {"la", "listAuthors"})
    public void listAuthors() {
        printModelService.printAllAuthors();
    }

    @ShellMethod(value = "list genres", key = {"lg", "listGenres"})
    public void listGenres() {
        printModelService.printAllGenres();
    }

    @ShellMethod(value = "insert book", key = {"i", "insert"})
    public void insert(@ShellOption(defaultValue = "default title") String title,
                       @ShellOption(defaultValue = "genre_name1") String genre,
                       @ShellOption(defaultValue = "author_name_01") String args) {
        String[] authors = Arrays.stream(args.split(",")).map(String::trim).toArray(String[]::new);
        bookService.insert(new BookRequestDto(null, title, genre, authors));
    }

    @ShellMethod(value = "get book by title", key = {"b", "book"})
    public void getBookByTitle(@ShellOption String title) {
        printModelService.printBookByTitle(title);
    }

    @ShellMethod(value = "get book by id", key = {"bi", "bookId"})
    public void getBookByTitle(@ShellOption Long id) {
        printModelService.printBookById(id);
    }

    @ShellMethod(value = "delete book by id", key = {"d", "delete"})
    public void deleteById(@ShellOption Long id) {
        bookService.deleteBookById(id);
    }

    @ShellMethod(value = "update book", key = {"u", "update"})
    public void updateById(@ShellOption Long id,
                           @ShellOption(value = "title") String title,
                           @ShellOption(value = "genre") String genre,
                           @ShellOption(value = "authors") String authorsList) {
        String[] authors = Arrays.stream(authorsList.split(",")).map(String::trim).toArray(String[]::new);
        bookService.update(new BookRequestDto(id, title, genre, authors));
    }

    @ShellMethod(value = "add comment", key = {"ac", "addComment"})
    public void addComment(@ShellOption(value = "comment", defaultValue = "default comment") String comment,
                           @ShellOption Long bookId) {
        commentService.addCommentForBook(new CommentRequestDto(null, comment, bookId));
    }

    @ShellMethod(value = "update comment", key = {"uc", "updateComment"})
    public void updateComment(@ShellOption Long id, @ShellOption String comment, @ShellOption Long bookId) {
        commentService.update(new CommentRequestDto(id, comment, bookId));
    }

    @ShellMethod(value = "list comment by bookId", key = {"lc", "listComments"})
    public void findAllByBookId(@ShellOption Long bookId) {
        printModelService.printAllCommentsByBookId(bookId);
    }

    @ShellMethod(value = "delete comment by id", key = {"dc", "deleteComments"})
    public void deleteComment(@ShellOption Long id) {
        commentService.deleteById(id);
    }
}
