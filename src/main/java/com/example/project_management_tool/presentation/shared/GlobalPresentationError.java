package com.example.project_management_tool.presentation.shared;

import com.example.project_management_tool.presentation.shared.error.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@ControllerAdvice
public class GlobalPresentationError {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<GlobalResponse.ErrorItem> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new GlobalResponse.ErrorItem(err.getField() + " " + err.getDefaultMessage()))
                .toList();
        return new ResponseEntity<>(new GlobalResponse(400, errors), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalResponse> handleValidationExceptions(HttpMessageNotReadableException ex) {

        List<GlobalResponse.ErrorItem> errors = List.of(new GlobalResponse.ErrorItem("Invalid JSON"));
        return new ResponseEntity<>(new GlobalResponse(400, errors), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomResponseException.class)
    public ResponseEntity<GlobalResponse> handleRuntimeExceptions(CustomResponseException ex) {
        List<GlobalResponse.ErrorItem> errors = List.of(new GlobalResponse.ErrorItem(ex.getMessage()));
        return new ResponseEntity<>(new GlobalResponse(ex.getCode(), errors), null, ex.getCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GlobalResponse> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        List<GlobalResponse.ErrorItem> errors = ex.getConstraintViolations().stream()
                .map(violation -> new GlobalResponse.ErrorItem(
                        violation.getPropertyPath() + " " + violation.getMessage()))
                .toList();
        return new ResponseEntity<>(new GlobalResponse(409, errors), null, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GlobalResponse> handleDataIntegrityViolationExceptions(DataIntegrityViolationException ex) {
        String fieldName = extractFieldFromMessage(ex.getMessage());
        List<GlobalResponse.ErrorItem> errors = List.of(new GlobalResponse.ErrorItem("This " + fieldName + " already exist."));
        return new ResponseEntity<>(new GlobalResponse(409, errors), null, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomNoFieldsToUpdateException.class)
    public ResponseEntity<GlobalResponse> handleCustomNoFieldsToUpdateException(CustomNoFieldsToUpdateException ex) {
        List<GlobalResponse.ErrorItem> errors = List.of(new GlobalResponse.ErrorItem(ex.getMessage()));
        return new ResponseEntity<>(new GlobalResponse(400, errors), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomResourceNotFoundException.class)
    public ResponseEntity<GlobalResponse> handleCustomResourceNotFoundException(CustomResourceNotFoundException ex) {
        List<GlobalResponse.ErrorItem> errors = List.of(new GlobalResponse.ErrorItem(ex.getMessage()));
        return new ResponseEntity<>(new GlobalResponse<>(404, errors), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomSystemRoleModificationException.class)
    public ResponseEntity<GlobalResponse> handleCustomSystemRoleModificationException(CustomSystemRoleModificationException ex) {
        List<GlobalResponse.ErrorItem> errors = List.of(new GlobalResponse.ErrorItem(ex.getMessage()));
        return new ResponseEntity<>(new GlobalResponse<>(403, errors), null, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GlobalResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        List<GlobalResponse.ErrorItem> errors = List.of(new GlobalResponse.ErrorItem(ex.getMessage()));
        return new ResponseEntity<>(new GlobalResponse<>(400, errors), null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomSystemRoleRequiredException.class)
    public ResponseEntity<GlobalResponse> handleCustomSystemRoleRequiredException(CustomSystemRoleRequiredException ex) {
        List<GlobalResponse.ErrorItem> errors = List.of(new GlobalResponse.ErrorItem(ex.getMessage()));
        return new ResponseEntity<>(new GlobalResponse<>(403, errors), null, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomDuplicateResourceException.class)
    public ResponseEntity<GlobalResponse> handleCustomDuplicateResourceException(CustomDuplicateResourceException ex) {
        List<GlobalResponse.ErrorItem> errors = List.of(new GlobalResponse.ErrorItem(ex.getMessage()));
        return new ResponseEntity<>(new GlobalResponse<>(409, errors), null, HttpStatus.CONFLICT);
    }


    private String extractFieldFromMessage(String message) {
        if (message.contains("Key (")) {
            try {
                int startIndex = message.indexOf("Key (") + 5;
                int endIndex = message.indexOf(")=", startIndex);
                if (endIndex > startIndex) {
                    return message.substring(startIndex, endIndex);
                }
            } catch (Exception e) {
                return "unknown field";
            }
        }
        return "unknown field";
    }

}
