package com.example.seeker.Model.Exception;

import com.example.seeker.Model.ApiResponse;

import java.util.List;

public class ApiException extends ApiResponse {
    private int status;
    private String message;
    private List<ApiError> errors;



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ApiError> getErrors() {
        return errors;
    }

    public void setErrors(List<ApiError> errors) {
        this.errors = errors;
    }
}
