package ru.job4j.principle_001;

/**
 * Pojo implementation of User.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class UserPojo implements User {
    /**
     * User's name.
     */
    private final String name;

    /**
     * User's age.
     */
    private final int age;

    /**
     * Constructor.
     * @param name user's name.
     * @param age user's age.
     */
    public UserPojo(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * ${@inheritDoc}.
     */
    @Override
    public String name() {
        return this.name;
    }

    /**
     * ${@inheritDoc}.
     */
    @Override
    public int age() {
        return this.age;
    }
}
