package com.transaction.statistics.exception;

public class AbstractException extends RuntimeException {

    public AbstractException(String code) {

        super();
        this.setCode(code);
    }


    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
