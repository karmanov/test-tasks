package io.karmanov.algorithms;

import java.util.*;

public class WorldCloud {

    public Map<String, Integer> buildCloud(String input) {
        String str = input
                .toLowerCase()
                .replace(",", "")
                .replace(".", "")
                .replace(":", "")
                .replace("!", "")
                .replace("?", "")
                ;
        Map<String, Integer> result = new HashMap<>();
        List<String> words = Arrays.asList(str.split(" "));
        for (String word : words) {
            int frequency = Collections.frequency(words, word);
            result.put(word, frequency);
        }
        return result;
    }
}
