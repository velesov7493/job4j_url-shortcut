package ru.job4j.urlshortcut.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.components.PasswordGenerator;
import ru.job4j.urlshortcut.components.UrlConverter;
import ru.job4j.urlshortcut.domains.Site;
import ru.job4j.urlshortcut.domains.Url;
import ru.job4j.urlshortcut.exceptions.OperationNotAcceptableException;
import ru.job4j.urlshortcut.repositories.SiteRepository;
import ru.job4j.urlshortcut.repositories.UrlRepository;

import java.util.List;

@Service
public class SiteService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteService.class);

    private final SiteRepository sites;
    private final UrlRepository urls;
    private final UrlConverter converter;
    private final PasswordGenerator passwords;

    public SiteService(
            SiteRepository sites, UrlRepository urls,
            UrlConverter converter, PasswordGenerator passwords
    ) {
        this.sites = sites;
        this.urls = urls;
        this.converter = converter;
        this.passwords = passwords;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Site result = findByLogin(username);
        if (result == null) {
            throw new UsernameNotFoundException(username);
        }
        return result;
    }

    public Site findByLogin(String login) {
        return sites.findByLogin(login).orElse(null);
    }

    public Site findSiteById(int siteId) {
        return sites.findById(siteId).orElse(null);
    }

    public Site saveSite(Site entity) throws OperationNotAcceptableException {
        Site result;
        try {
            if (entity.getId() == 0) {
                entity.setPassword(passwords.generate(20));
                entity.setEnabled(true);
            }
            result = sites.save(entity);
        } catch (Exception ex) {
            LOG.error("Ошибка сохранения сайта: ", ex);
            throw new OperationNotAcceptableException(ex);
        }
        return result;
    }

    public Url findUrlByLink(String link) {
        return urls.findByLink(link).orElse(null);
    }

    public Url findUrlAndIncrementCallsByCode(String code) {
        Url result = null;
        try {
            result = urls.incrementAndGetByCode(code).orElse(null);
        } catch (Exception ex) {
            LOG.error("Ошибка выполнения запроса: ", ex);
        }
        return result;
    }

    public Url saveUrl(Url entity) throws OperationNotAcceptableException {
        Url result;
        try {
            result = urls.save(entity);
            result.setCode(converter.convert(result));
            result = urls.save(result);
        } catch (Exception ex) {
            LOG.error("Ошибка сохранения url: ", ex);
            throw new OperationNotAcceptableException(ex);
        }
        return result;
    }

    public List<Url> findAllUrlBySiteId(int siteId) {
        return urls.findAllBySiteId(siteId);
    }
}