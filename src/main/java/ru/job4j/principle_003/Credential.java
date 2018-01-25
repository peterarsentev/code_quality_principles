package ru.job4j.principle_003;

import ru.job4j.principle_001.User;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Credential {

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

    boolean hasAccess(final User user) {
        this.validates.forEach(action -> action.accept(user));
        return true;
    }

    private boolean checkBalance(User user) {
        return false;
    }

    private boolean checkSurname(User user) {
        return false;
    }

    private boolean checkName(User user) {
        return false;
    }
}
