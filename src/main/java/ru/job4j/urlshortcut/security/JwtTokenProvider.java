package ru.job4j.urlshortcut.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.job4j.urlshortcut.domains.Site;
import ru.job4j.urlshortcut.exceptions.JwtAuthenticationException;
import ru.job4j.urlshortcut.services.SiteService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private SiteService sites;
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long expirationTime;
    @Value("${jwt.token.prefix}")
    private String prefix;
    @Value("${jwt.token.header}")
    private String headerName;

    private String resolveToken(String token) {
        return
                token != null
                ? token.startsWith(prefix)
                        ? token.replace(prefix, "") : token
                : null;
    }

    private String getUsername(String token) {
        return
                token == null ? null
                : JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build().verify(resolveToken(token))
                .getSubject();
    }

    String createTokenByUsername(String username) {
        return
                prefix
                + JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    boolean validateToken(String token) {
        return
                token != null
                && !JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build().verify(token)
                .getExpiresAt().before(new Date());
    }

    Authentication getAuthentication(String token) throws JwtAuthenticationException {
        Site s = getSite(token);
        return s == null ? null
               : new UsernamePasswordAuthenticationToken(
                     s.getLogin(), s.getPassword(), s.getAuthorities()
                 );
    }

    String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(headerName);
        return resolveToken(bearerToken);
    }

    String getAuthHeaderName() {
        return headerName;
    }

    public Site getSite(String token) throws JwtAuthenticationException {
        String login = getUsername(token);
        Site result = sites.findByLogin(login);
        if (result == null) {
            throw new JwtAuthenticationException();
        }
        return result;
    }

    public void setSiteService(SiteService value) {
        sites = value;
    }
}