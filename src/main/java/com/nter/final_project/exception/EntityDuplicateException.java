package com.nter.final_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityDuplicateException extends RuntimeException {
    public EntityDuplicateException(String userName) {
        super("El usuario: "+userName+" ya existe");
    }
}
