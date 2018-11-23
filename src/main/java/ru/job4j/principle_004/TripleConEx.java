package ru.job4j.principle_004;

/**
 * It takes three args.
 * @param <F> first.
 * @param <S> second.
 * @param <T> third.
 */
public interface TripleConEx<F, S, T> {
    /**
     * It takes three args.
     * @param first first.
     * @param second second.
     * @param third third.
     * @throws Exception possible exception.
     */
    void accept(F first, S second, T third) throws Exception;
}
