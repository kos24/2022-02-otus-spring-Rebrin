package ru.otus.service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceConsole implements IOService {

    private final PrintStream writer;
    private final Scanner reader;

    public IOServiceConsole(PrintStream writer, InputStream reader) {
        this.writer = writer;
        this.reader = new Scanner(reader);
    }

    @Override
    public void print(String message) {
        writer.println(message);
    }

    @Override
    public String read() {
        return reader.nextLine();
    }

    @Override
    public int readInt() {
        return reader.nextInt();
    }
}
