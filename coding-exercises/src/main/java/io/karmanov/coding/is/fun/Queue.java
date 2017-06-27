package io.karmanov.coding.is.fun;

import java.util.Stack;

public class Queue<T> {

    private Stack<T> s1 = new Stack<>();
    private Stack<T> s2 = new Stack<>();

    public void enQueue(T item) {
        s1.push(item);
    }

    public T deQueue() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
            return s2.pop();
        }
        return s2.pop();
    }
}
