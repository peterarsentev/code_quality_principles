package ru.job4j.principle_004;

public interface ConEx<T> {
    void accept(T param) throws Exception;
}
