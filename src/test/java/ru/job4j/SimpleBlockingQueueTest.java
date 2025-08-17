package ru.job4j;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleBlockingQueueTest {
    @Test
    @Timeout(value = 1)
    public void whenAdd() throws InterruptedException {
        var queue = new SimpleBlockingQueue<Integer>(2);
        var one = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    try {
                        queue.offer(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                "One"
        );
        var two = new Thread(
                () -> {
                    try {
                        queue.offer(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Two"
        );
        one.start();
        two.start();
        one.join();
        two.join();
        var rsl = List.of(queue.poll(), queue.poll());
        assertThat(rsl).containsOnly(1, 3);
    }
}
