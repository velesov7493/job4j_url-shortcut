package ru.job4j.urlshortcut.components;

class StringUtils {

    static final int ALPHABET_SIZE = 62;

    static char charByIndex(int index) {
        if (index < 0 || index >= ALPHABET_SIZE) {
            throw new IndexOutOfBoundsException(index);
        }
        char result;
        if (index <= 9) {
            result = (char) ('0' + index);
        } else if (index < 36) {
            result = (char) ('A' + index - 10);
        } else {
            result = (char) ('a' + index - 36);
        }
        return result;
    }
}
