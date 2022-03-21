package ru.job4j.urlshortcut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.job4j.urlshortcut.components.NoChangesPasswordEncoder;
import ru.job4j.urlshortcut.components.PasswordGenerator;
import ru.job4j.urlshortcut.components.UrlConverter;

@SpringBootApplication
public class UrlShortcutApplication {

    @Bean
    public UrlConverter getUrlConverter() {
        return new UrlConverter();
    }

    @Bean
    public PasswordGenerator getPasswordGenerator() {
        return new PasswordGenerator();
    }

    @Bean
    public NoChangesPasswordEncoder getPasswordEncoder() {
        return new NoChangesPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(UrlShortcutApplication.class, args);
    }

}
