package ru.job4j.urlshortcut.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.job4j.urlshortcut.exceptions.JwtAuthenticationException;
import ru.job4j.urlshortcut.exceptions.ObjectNotFoundException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider provider;

    public JwtAuthorizationFilter(
            AuthenticationManager authenticationManager,
            JwtTokenProvider tokenProvider
    ) {
        super(authenticationManager);
        provider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {

        String token = provider.resolveToken(req);
        if (token != null && provider.validateToken(token)) {
            try {
                Authentication authentication = provider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtAuthenticationException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
        chain.doFilter(req, res);
    }
}