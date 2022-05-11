package ru.job4j.urlshortcut.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.job4j.urlshortcut.dto.AuthenticationDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager auth;
    private final JwtTokenProvider provider;

    public JwtAuthenticationFilter(
            AuthenticationManager auth,
            JwtTokenProvider provider
    ) {
        this.auth = auth;
        this.provider = provider;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException {

        Authentication result;
        try {
            AuthenticationDto creds =
                    new ObjectMapper().readValue(
                            request.getInputStream(),
                            AuthenticationDto.class
                    );
            result = auth.authenticate(
                        new UsernamePasswordAuthenticationToken(
                            creds.getSite(),
                            creds.getPassword(),
                            new ArrayList<>()
                        )
            );
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authResult
    ) throws IOException, ServletException {

        String username = ((UserDetails) authResult.getPrincipal()).getUsername();
        String token = provider.createTokenByUsername(username);
        response.addHeader(provider.getAuthHeaderName(), token);
    }
}