package com.micro.services.product.content.svc.exception;

public class ContentServiceException extends Exception {

    public enum ErrorCode {
        PRODUCT_DOES_NOT_EXIST,
    }

    private ErrorCode errorCode;

    public ContentServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
