package ru.job4j.principle_003;

import java.util.Map;

/**
 * Validate data.
 */
public interface Validate {
    /**
     * All user.
     * @return map.
     */
    Map<Integer, User> findAll();

    /**
     * Add user to store.
     * @param user user.
     */
    void add(User user);

    /**
     * Delete user in store.
     * @param id user's id.
     */
    void delete(Integer id);

    /**
     * Update user.
     * @param id id.
     * @param user user.
     */
    void update(Integer id, User user);
}
