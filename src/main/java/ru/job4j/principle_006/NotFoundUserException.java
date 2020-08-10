package ru.job4j.principle_006;

public class NotFoundUserException extends Exception {
    public NotFoundUserException(String message) {
        super(message);
    }
}
