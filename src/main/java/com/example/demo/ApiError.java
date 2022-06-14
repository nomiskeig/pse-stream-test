

package com.example.demo;

import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Arrays;
public class ApiError {

    private HttpStatus status;
    private String message;

    

    public ApiError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }
    public HttpStatus getStatus() {
        return this.status;
    }
    public String getMessage() {
        return this.message;
    }
}
