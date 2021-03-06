package com.example.seeker.Model.Exception;

public class ApiError {
    private String field;
    private String message;

    public ApiError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public ApiError(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
