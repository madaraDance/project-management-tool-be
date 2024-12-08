package com.example.project_management_tool.presentation.shared.error;

public class CustomNoFieldsToUpdateException extends RuntimeException {
    public CustomNoFieldsToUpdateException(String message) {
        super(message);
    }
}
