package ru.job4j.principle_003;

import java.util.Map;

public enum ValidateService implements Validate {
    INSTANCE;

    @Override
    public Map<Integer, User> findAll() {
        return null;
    }

    @Override
    public void add(User user) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Integer id, User user) {

    }
}
