package ru.job4j.principle_003;

import java.util.Objects;

public class User {
    private String name;
    private String login;
    private String email;
    private String created;

    public User(String name, String login, String email, String create) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(login, user.login) &&
                Objects.equals(email, user.email) &&
                Objects.equals(created, user.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login, email, created);
    }
}
