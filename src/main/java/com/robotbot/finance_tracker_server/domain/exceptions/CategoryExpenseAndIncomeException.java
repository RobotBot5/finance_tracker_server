package com.robotbot.finance_tracker_server.domain.exceptions;

public class CategoryExpenseAndIncomeException extends RuntimeException {
    public CategoryExpenseAndIncomeException(String message) {
        super(message);
    }

    public CategoryExpenseAndIncomeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryExpenseAndIncomeException(Throwable cause) {
        super(cause);
    }

    public CategoryExpenseAndIncomeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CategoryExpenseAndIncomeException() {
    }
}
