package com.robotbot.finance_tracker_server.domain.exceptions;

public class ExchangeApiException extends RuntimeException {

    public ExchangeApiException(String message) {
        super(message);
    }

    public ExchangeApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExchangeApiException(Throwable cause) {
        super(cause);
    }

    public ExchangeApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExchangeApiException() {
    }
}
