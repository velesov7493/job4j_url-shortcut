package ru.job4j.urlshortcut.domains;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tz_links")
public class Url {

    @Id
    @SequenceGenerator(name = "linksIdSeq", sequenceName = "tz_links_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "linksIdSeq")
    private long id;
    @Column(name = "id_site")
    private int siteId;
    private String code;
    private String link;
    private long calls;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public long getCalls() {
        return calls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Url url = (Url) o;
        return
                siteId == url.siteId
                && Objects.equals(link, url.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(siteId, link);
    }
}