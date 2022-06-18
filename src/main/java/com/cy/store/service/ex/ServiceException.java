package com.cy.store.service.ex;

/**
 * 业务类异常的基类
 */
public class ServiceException extends RuntimeException {
    private static String fromService = "Message from Service layer: ";
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(fromService + message);
    }

    public ServiceException(String message, Throwable cause) {
        super(fromService + message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(fromService + message, cause, enableSuppression, writableStackTrace);
    }
}
