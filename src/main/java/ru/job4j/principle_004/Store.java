package ru.job4j.principle_004;

import java.util.List;

public interface Store<T> {
    T add(T model);
    void update(T model);
    void delete(int id);
    List<T> findAll();
    T findById(int id);
}
