package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceConsole implements IOService {

    private PrintStream writer = System.out;
    private Scanner reader = new Scanner(System.in);

    @Autowired
    public IOServiceConsole() {
    }

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
