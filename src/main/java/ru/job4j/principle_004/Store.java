package ru.job4j.principle_004;

import java.util.List;

/**
 * It describes a store.
 * @param <T>
 */
public interface Store<T> {
    /**
     * Save model.
     * @param model Model.
     * @return stored model.
     */
    T add(T model);

    /**
     * Update model.
     * @param model model.
     */
    void update(T model);

    /**
     * Delete model from a store.
     * @param id model's id.
     */
    void delete(int id);

    /**
     * Get all stored models.
     * @return list of models.
     */
    List<T> findAll();

    /**
     * Get model by id.
     * @param id model's id.
     * @return model.
     */
    T findById(int id);
}
