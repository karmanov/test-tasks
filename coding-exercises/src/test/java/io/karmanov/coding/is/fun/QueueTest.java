package io.karmanov.coding.is.fun;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueueTest {

    @Test
    public void simpleOneInOneOut() throws Exception {
        Queue<Integer> intQueue = new Queue<>();
        intQueue.enQueue(1);
        Integer result = intQueue.deQueue();
        assertEquals(1, result.intValue());
    }

    @Test
    public void TwoInOneOut() throws Exception {
        Queue<Integer> intQueue = new Queue<>();
        intQueue.enQueue(1);
        intQueue.enQueue(2);
        Integer result = intQueue.deQueue();
        assertEquals(1, result.intValue());
    }

    @Test
    public void differentOperations() throws Exception {
        Queue<Integer> intQueue = new Queue<>();
        intQueue.enQueue(1);
        intQueue.enQueue(2);
        intQueue.enQueue(3);
        Integer result1 = intQueue.deQueue();
        intQueue.enQueue(4);

        Integer result2 = intQueue.deQueue();

        assertEquals(1, result1.intValue());
        assertEquals(2, result2.intValue());

    }
}