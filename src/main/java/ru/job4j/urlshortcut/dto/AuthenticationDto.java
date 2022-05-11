package ru.job4j.urlshortcut.dto;

public class AuthenticationDto {

    private String site;
    private String password;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
