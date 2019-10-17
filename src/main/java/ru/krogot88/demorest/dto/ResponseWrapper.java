package ru.krogot88.demorest.dto;

import org.springframework.http.HttpStatus;
import ru.krogot88.demorest.model.Word;

/**
 * User: Сашок  Date: 14.10.2019 Time: 22:45
 */
public class ResponseWrapper<T> {

    private T entity;
    private HttpStatus httpStatus;

    public ResponseWrapper() {
    }

    public ResponseWrapper(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ResponseWrapper(T entity, HttpStatus httpStatus) {
        this.entity = entity;
        this.httpStatus = httpStatus;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
