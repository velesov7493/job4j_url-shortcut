package ru.job4j.urlshortcut.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.urlshortcut.domains.Url;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {

    @Query(value = "SELECT * FROM incrementcallsandgeturl(?1)", nativeQuery = true)
    Optional<Url> incrementAndGetByCode(String code);

    List<Url> findAllBySiteId(int siteId);

    Optional<Url> findByLink(String link);
}
