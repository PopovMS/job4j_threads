package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static Thread progress = new Thread(new ConsoleProgress());


    public static void main(String[] args) throws InterruptedException {
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();

    }

    @Override
    public void run() {
        var process = new char[] {'-', '\\', '|', '/'};
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            if (index == process.length) {
                index = 0;
            }
            System.out.print("\r load: " + process[index++]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
