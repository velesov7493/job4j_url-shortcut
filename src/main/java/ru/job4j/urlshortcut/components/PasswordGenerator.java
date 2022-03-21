package ru.job4j.urlshortcut.components;

import java.util.Random;

import static ru.job4j.urlshortcut.components.StringUtils.ALPHABET_SIZE;

public class PasswordGenerator {

    public String generate(int length) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(StringUtils.charByIndex(rnd.nextInt(ALPHABET_SIZE)));
        }
        return sb.toString();
    }
}
