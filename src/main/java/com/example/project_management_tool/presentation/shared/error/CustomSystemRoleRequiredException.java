package com.example.project_management_tool.presentation.shared.error;

public class CustomSystemRoleRequiredException extends RuntimeException {
    public CustomSystemRoleRequiredException(String message) {
        super(message);
    }
}
