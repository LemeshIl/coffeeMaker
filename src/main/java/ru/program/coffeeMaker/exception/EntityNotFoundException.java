package ru.program.coffeeMaker.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Long id) {
        super("Could not find entity " + id);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
