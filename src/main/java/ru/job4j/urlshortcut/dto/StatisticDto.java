package ru.job4j.urlshortcut.dto;

import ru.job4j.urlshortcut.domains.Url;

import java.util.Objects;

public class StatisticDto {

    private String url;
    private long total;

    public static StatisticDto of(Url source) {
        StatisticDto result = new StatisticDto();
        result.url = source.getLink();
        result.total = source.getCalls();
        return result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StatisticDto that = (StatisticDto) o;
        return
                total == that.total
                && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, total);
    }
}
