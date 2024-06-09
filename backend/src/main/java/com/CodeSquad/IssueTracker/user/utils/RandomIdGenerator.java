package com.CodeSquad.IssueTracker.user.utils;

import java.util.Random;

public class RandomIdGenerator {
    private static final String[] ADJECTIVES = {
            "Quick", "Lazy", "Happy", "Sad", "Brave", "Young", "Witty", "Calm", "Eager", "Silly"
    };

    private static final String[] NOUNS = {
            "Lion", "Tiger", "Cat", "Giraffe", "Panda", "Fox", "Wolf", "Bear", "Eagle", "Shark"
    };

    public static String generateRandomId() {
        Random random = new Random();
        String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[random.nextInt(NOUNS.length)];
        int number = random.nextInt(9000) + 1000;

        String randomId = adjective + noun + number;
        return randomId;
    }
}
