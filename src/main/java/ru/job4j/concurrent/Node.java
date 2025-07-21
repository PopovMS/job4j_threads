package ru.job4j.concurrent;

public final class Node<T> {
    /* 1. Все поля должны быть final */
    private final Node<T> next;
    private final T value;

    /* 2. Добавляем конструктор для инициализации полей */
    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    /* Оставляем только геттеры */
    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}