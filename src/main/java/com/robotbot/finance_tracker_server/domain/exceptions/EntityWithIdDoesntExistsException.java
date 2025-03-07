package com.robotbot.finance_tracker_server.domain.exceptions;

public class EntityWithIdDoesntExistsException extends RuntimeException {
    public EntityWithIdDoesntExistsException(String message) {
        super(message);
    }

    public EntityWithIdDoesntExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityWithIdDoesntExistsException(Throwable cause) {
        super(cause);
    }

    public EntityWithIdDoesntExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EntityWithIdDoesntExistsException() {
    }
}
