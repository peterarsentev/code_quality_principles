package ru.job4j.principle_004;

public interface FunEx<T, R> {
    R apply(T param) throws Exception;
}
