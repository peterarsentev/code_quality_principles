package ru.job4j.principle_004;

/**
 * External resource.
 * @param <T> type
 */
public interface ExtResource<T> {

    /**
     * Read resource by name.
     * @param name resources' name
     * @return value.
     * @throws Exception possible exception.
     */
    T read(String name) throws Exception;

    /**
     * Write resource.
     * @param value resource.
     * @throws Exception possible exception.
     */
    void write(T value) throws Exception;
}
