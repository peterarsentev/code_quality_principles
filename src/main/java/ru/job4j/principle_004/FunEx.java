package ru.job4j.principle_004;

/**
 * Funcation interface. It takes param and return data.
 * @param <T> input arg.
 * @param <R> return data.
 */
public interface FunEx<T, R> {
    /**
     * Take param and return value.
     * @param param input param
     * @return value.
     * @throws Exception possible exception.
     */
    R apply(T param) throws Exception;
}
