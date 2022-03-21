package ru.job4j.urlshortcut.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.job4j.urlshortcut.components.NoChangesPasswordEncoder;
import ru.job4j.urlshortcut.security.JwtAuthenticationFilter;
import ru.job4j.urlshortcut.security.JwtAuthorizationFilter;
import ru.job4j.urlshortcut.security.JwtTokenProvider;
import ru.job4j.urlshortcut.services.SiteService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SIGN_UP_URL = "/registration";
    private static final String REDIRECT_URL = "/redirect/**";

    private final SiteService details;
    private final JwtTokenProvider provider;
    private final PasswordEncoder encoder;

    public WebSecurityConfig(
            SiteService details,
            JwtTokenProvider provider,
            NoChangesPasswordEncoder encoder
    ) {
        this.details = details;
        this.provider = provider;
        this.encoder = encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(details).passwordEncoder(encoder);
        provider.setSiteService(details);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, REDIRECT_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(
                        new JwtAuthenticationFilter(authenticationManager(), provider),
                        UsernamePasswordAuthenticationFilter.class
                ).addFilterBefore(
                        new JwtAuthorizationFilter(authenticationManager(), provider),
                        BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}