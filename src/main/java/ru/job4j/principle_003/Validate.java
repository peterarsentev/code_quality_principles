package ru.job4j.principle_003;

import java.util.Map;

public interface Validate {
    Map<Integer, User> findAll();

    void add(User user);

    void delete(Integer id);

    void update(Integer id, User user);
}
