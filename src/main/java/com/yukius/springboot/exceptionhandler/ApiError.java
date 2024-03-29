package com.yukius.springboot.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;


@Getter
@Setter
public class ApiError{

    private HttpStatus status;
    /**Beautify JSON ResponseBody*/
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy @ HH:mm:ss")
    private Date timestamp;
    private String message;

    public ApiError(HttpStatus status, Throwable ex) {
        this.timestamp = new Date();
        this.status = status;
        this.message =ex.getMessage();
    }
    public ApiError(HttpStatus status, Throwable ex, String message) {
        this.timestamp = new Date();
        this.status = status;
        this.message = message;
    }
}
