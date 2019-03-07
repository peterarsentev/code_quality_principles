package ru.job4j.principle_003;

import java.util.Objects;

/**
 * Model user.
 */
public class User {
    /**
     * Name.
     */
    private String name;
    /**
     * Login.
     */
    private String login;
    /**
     * Email.
     */
    private String email;
    /**
     * Create's date.
     */
    private String created;

    /**
     * Constructor.
     * @param name Name.
     * @param login Login.
     * @param email Email.
     * @param created Create's date.
     */
    public User(String name, String login, String email, String created) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.created = created;
    }

    /**
     * Get name.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get login.
     * @return Login.
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
     * Get email.
     * @return Email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email.
     * @param email email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get create's date.
     * @return Date
     */
    public String getCreated() {
        return created;
    }

    /**
     * Set create's date.
     * @param created created.
     */
    public void setCreated(String created) {
        this.created = created;
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
        User user = (User) o;
        return Objects.equals(name, user.name)
                && Objects.equals(login, user.login)
                && Objects.equals(email, user.email)
                && Objects.equals(created, user.created);
    }

    /**
     * ${@inheritDoc}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, login, email, created);
    }
}
