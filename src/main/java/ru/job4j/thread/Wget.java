package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.regex.Pattern;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                var readPackSpeed = System.nanoTime() - downloadAt;
                var byteInMs = (512D / (double) readPackSpeed) * 1000000D;
                System.out.println("byteInMs = " + byteInMs);
                if (byteInMs > (double) speed) {
                    Long sleepTime = (long)  (byteInMs / (long) speed);
                    Thread.sleep(sleepTime);
                    System.out.println("sleep " + sleepTime + " ms");
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        check(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    private static void check(String[] args) {
        if (args.length <= 1) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        if (!Pattern.compile("(https?|ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]").matcher(args[0]).matches()) {
            throw new IllegalArgumentException("Error: This argument '%s' does not contain a value".formatted(args[0]));
        }
        if (!Pattern.compile("^[0-9]+$").matcher(args[1]).matches()) {
            throw new IllegalArgumentException("Error: This argument '%s' does not contain a value".formatted(args[1]));
        }
    }
}