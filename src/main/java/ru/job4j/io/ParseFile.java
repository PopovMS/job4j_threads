package ru.job4j.io;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return readFile(data -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return readFile(data -> data < 0x80);
    }

    private String readFile(Predicate<Integer> predicate) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = bufferedReader.read()) != -1) {
                if (predicate.test(data)) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }
}
