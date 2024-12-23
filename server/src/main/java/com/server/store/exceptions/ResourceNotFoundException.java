package com.server.store.exceptions;

/**
 * Исключение, выбрасываемое, когда ресурс не найден.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Конструктор, принимающий сообщение об ошибке.
     *
     * @param message сообщение об ошибке
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
