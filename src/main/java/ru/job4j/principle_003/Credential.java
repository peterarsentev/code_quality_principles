package ru.job4j.principle_003;

import ru.job4j.principle_001.User;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Example of multiple if-else-throw statements.
 */
public class Credential {

    /**
     * Validations rules.
     */
    private final List<Consumer<User>> validates = Arrays.asList(
            user -> {
                if (!this.checkName(user)) {
                    throw new IllegalStateException("Wrong name.");
                }
            },
            user -> {
                if (!this.checkSurname(user)) {
                    throw new IllegalStateException("Wrong surname.");
                }
            },
            user -> {
                if (!this.checkBalance(user)) {
                    throw new IllegalStateException("Wrong balance.");
                }
            }
    );

    /**
     * Check the access for user by validations rules.
     * @param user User.
     * @return true if user has the access.
     */
    boolean hasAccess(final User user) {
        this.validates.forEach(action -> action.accept(user));
        return true;
    }

    /**
     * Validate by users' balance.
     * @param user User
     * @return true if users' balance is valid
     */
    private boolean checkBalance(User user) {
        return false;
    }

    /**
     * Validate by users' surname.
     * @param user User
     * @return true if users' surname is valid
     */
    private boolean checkSurname(User user) {
        return false;
    }

    /**
     * Validate by users' name.
     * @param user User
     * @return true if users' name is valid
     */
    private boolean checkName(User user) {
        return false;
    }
}
