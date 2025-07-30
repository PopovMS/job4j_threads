package ru.job4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleBlockingQueueTest {
    @Test
    public void whenAdd() throws InterruptedException {
        SimpleBlockingQueue blockingQueue = new SimpleBlockingQueue(1);
        Thread one = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    blockingQueue.offer(1);
                },
                "One"
        );
        Thread two = new Thread(
                () -> {
                    blockingQueue.offer(3);
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Two"
        );
        one.start();
        two.start();
        assertThat(blockingQueue.poll()).isEqualTo(1);
    }

    @Test
    public void whenAddAndPoolThenAdd() throws InterruptedException {
        SimpleBlockingQueue blockingQueue = new SimpleBlockingQueue(1);
        Thread one = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    blockingQueue.offer(1);
                },
                "One"
        );
        Thread two = new Thread(
                () -> {
                    blockingQueue.offer(2);
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Two"
        );
        Thread three = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    try {
                        blockingQueue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                "Three"
        );
        one.start();
        two.start();
        three.start();
        assertThat(blockingQueue.poll()).isEqualTo(2);
    }
}
