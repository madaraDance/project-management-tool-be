package com.example.project_management_tool.presentation.shared.error;

public class CustomDuplicateResourceException extends RuntimeException {
    public CustomDuplicateResourceException(String message) {
        super(message);
    }
}
