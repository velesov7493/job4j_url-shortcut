package ru.job4j.urlshortcut.dto;

import ru.job4j.urlshortcut.domains.Site;

public class RegisterRespDto {

    private boolean registration;
    private String login;
    private String password;

    public static RegisterRespDto of(boolean registered, Site source) {
        RegisterRespDto result = new RegisterRespDto();
        result.registration = registered;
        result.login = source.getLogin();
        result.password = source.getPassword();
        return result;
    }

    public boolean isRegistration() {
        return registration;
    }

    public void setRegistration(boolean registration) {
        this.registration = registration;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}