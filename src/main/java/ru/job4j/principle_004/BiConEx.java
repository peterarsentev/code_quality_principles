package ru.job4j.principle_004;

public interface BiConEx<L, R> {
    void accept(L left, R right) throws Exception;
}
