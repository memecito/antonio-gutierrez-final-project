package com.nter.final_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String tipo, Long id) {
      super("No se puede encontrar "+tipo+" con id: "+id);
    }

    public EntityNotFoundException(String name){
        super("No se puede encontrar a la persona con el nombre: "+name);
    }
}
