package ru.job4j.principle_004;

public interface TripleConEx<F, S, T> {
    void accept(F first, S second, T third) throws Exception;
}
