package com.nter.final_project.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomError {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    Map<String, String> details;

    public CustomError(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public CustomError(int status, String error, Map<String, String> details) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.details = details;
    }
}
