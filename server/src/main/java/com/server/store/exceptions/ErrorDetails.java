package com.server.store.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Класс, представляющий детали ошибки.
 */
@Data
@AllArgsConstructor
public class ErrorDetails {

    /**
     * Временная метка ошибки.
     */
    private Date timestamp;

    /**
     * Сообщение об ошибке.
     */
    private String message;

    /**
     * Дополнительные детали ошибки.
     */
    private String details;
}
