package ru.job4j.principle_004;

import java.util.Objects;

/**
 * User model.
 */
public class User {
    /**
     * Identification.
     */
    private int id;
    /**
     * Login.
     */
    private String login;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Takes id and login.
     * @param id id.
     * @param login login.
     */
    public User(int id, String login) {
        this.id = id;
        this.login = login;
    }

    /**
     * Get id.
     * @return id.
     */
    public int getId() {
        return id;
    }

    /**
     * Set id.
     * @param id id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get login.
     * @return login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Set login.
     * @param login login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * ${@inheritDoc}.
     */
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login='" + login + '\'' + '}';
    }

    /**
     * ${@inheritDoc}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user1 = (User) o;
        return id == user1.id && Objects.equals(login, user1.login);
    }

    /**
     * ${@inheritDoc}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }
}
