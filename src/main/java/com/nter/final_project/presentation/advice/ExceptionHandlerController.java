package com.nter.final_project.presentation.advice;

import com.nter.final_project.exception.*;
import com.nter.final_project.presentation.dto.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(
                (error) -> {
                    String key = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    errors.put(key, message);
                }
        );
        CustomError customError = new CustomError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Faltan campos",
                errors
        );
        return new ResponseEntity<>(customError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class, UserNotFounException.class})
    public ResponseEntity<CustomError> handleEntityNotFound(RuntimeException ex) {
        CustomError customError = new CustomError(
                HttpStatus.NOT_FOUND.value(),
                "Entidad no encontrada",
                ex.getMessage()
        );
        return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CustomError> handleMissinParams(MissingServletRequestParameterException ex) {
        CustomError customError = new CustomError(
                HttpStatus.BAD_REQUEST.value(),
                "Faltan algunos parametros",
                ex.getMessage()
        );
        return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<CustomError> handleForbidenPath(MissingPathVariableException ex) {
        CustomError customError = new CustomError(
                HttpStatus.I_AM_A_TEAPOT.value(),
                "Direccion no permitida",
                ex.getMessage()
        );
        return new ResponseEntity<>(customError, HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(value = {
            UnauthorizedException.class,
            UnauthenticatedException.class
    })
    public ResponseEntity<CustomError> handleFUnauthorizedException(Exception ex) {
        CustomError customError = new CustomError(
                HttpStatus.I_AM_A_TEAPOT.value(),
                "Direccion no permitida",
                ex.getMessage()
        );
        return new ResponseEntity<>(customError, HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(value = {
            ForbiddenOperationException.class
    })
    public ResponseEntity<CustomError> handleForbidenOperationException(ForbiddenOperationException ex) {
        CustomError customError = new CustomError(
                HttpStatus.FORBIDDEN.value(),
                "Operacion no permitida",
                ex.getMessage()
        );
        return new ResponseEntity<>(customError, HttpStatus.I_AM_A_TEAPOT);
    }

    /*
    @ExceptionHandler()
    public ResponseEntity<CustomError> handleUnauthenticatedException(UnauthenticatedException ex) {
        CustomError customError = new CustomError(
                HttpStatus.I_AM_A_TEAPOT.value(),
                "Direccion no permitida",
                ex.getMessage()
        );
        return new ResponseEntity<>(customError, HttpStatus.I_AM_A_TEAPOT);
    }

     */

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<CustomError> handleTokenException(InvalidTokenException ex) {
        CustomError customError = new CustomError(
                HttpStatus.UNAUTHORIZED.value(),
                "Token no valido",
                ex.getMessage()
        );
        return new ResponseEntity<>(customError, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomError> handleBadRequestException(BadRequestException ex) {
        CustomError customError = new CustomError(
                HttpStatus.I_AM_A_TEAPOT.value(),
                "Mala Respuesta",
                ex.getMessage()
        );
        return new ResponseEntity<>(customError, HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(value = {
            EmailAlreadyExistException.class,
            EntityDuplicateException.class})
    public ResponseEntity<CustomError> handleBadRequestException(Exception ex) {
        CustomError customError = new CustomError(
                HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value(),
                "Duplicado",
                ex.getMessage()
        );
        return new ResponseEntity<>(customError, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class
    })
    public ResponseEntity<CustomError> hadleIllegalArguement(IllegalArgumentException ex) {
        CustomError customError = new CustomError(
                HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value(),
                "Argumento no valido",
                ex.getMessage()
        );
        return new ResponseEntity<>(customError, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }
}
