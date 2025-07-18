package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static Thread progress = new Thread(new ConsoleProgress());


    public static void main(String[] args) throws InterruptedException {
        progress.start();
        /* System.out.println("Имя нити = " + Thread.currentThread().getName()); */
        Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        /* System.out.println("Нить прервана ? - " + progress.isInterrupted()); */
        progress.interrupt();
        System.out.println("Нить прервана ? - " + progress.isInterrupted());

    }

    @Override
    public void run() {
        var process = new char[] {'-', '\\', '|', '/'};
        int index = 0;
        System.out.println("Нить прервана (внутри метода RUN) ? - " + progress.isInterrupted());
        while (!progress.isInterrupted()) {
            if (index == process.length) {
                index = 0;
            }
            /* System.out.println(progress.getName()); */
            System.out.print("\r load: " + process[index++]);
            /* System.out.println(progress.isInterrupted()); */
            try {
                progress.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
