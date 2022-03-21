package ru.job4j.urlshortcut.components;

import ru.job4j.urlshortcut.domains.Url;

import static ru.job4j.urlshortcut.components.StringUtils.ALPHABET_SIZE;

public class UrlConverter {

    private String convertHash(long hash) {
        long d = hash;
        StringBuilder r = new StringBuilder();
        while (d > 0) {
            int i = (int) (d % ALPHABET_SIZE);
            r.append(StringUtils.charByIndex(i));
            d = d / ALPHABET_SIZE;
        }
        return r.toString();
    }

    private long longHash(Url value) {
        return
                Long.rotateLeft(value.getId(),  32 + value.getSiteId() % 5)
                ^ Long.rotateRight(
                        value.hashCode(), (int) (value.getSiteId() + 31 * value.getId()) % 12
                ) & 0x7FFFFFFFFFFFFFFFL;
    }

    public String convert(Url value) {
        return convertHash(longHash(value));
    }
}