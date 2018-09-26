package io.karmanov.algorithms;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class WorldCloudTest {

    private String SIMPLE_INPUT = "After beating the eggs, Dana read the next step:";
    private String SIMPLE_INPUT_2 = "Add milk and eggs, then add flour and sugar.";

    @Test
    public void buildCloud_simple_input() {
        WorldCloud wc = new WorldCloud();
        Map<String, Integer> result1 = wc.buildCloud(SIMPLE_INPUT);
        assertEquals(8, result1.size());
        printMap(result1);

        Map<String, Integer> result2 = wc.buildCloud(SIMPLE_INPUT_2);
        assertEquals(7, result2.size());
        printMap(result2);
    }


    private void printMap(Map<String, Integer> map) {
        System.out.println("-------------------------------");
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            System.out.println("Key: " + e.getKey() + " Value: " + e.getValue());
            System.out.println("*****************************************");
        }
    }
}
