package com.example.project_management_tool.domain.enums;

public enum PermissionName {
    COMPANY_UPDATE("123e4567-e89b-12d3-a456-426614174000"),
    COMPANY_READ("123e4567-e89b-12d3-a456-426614174001"),
    USER_CREATE("123e4567-e89b-12d3-a456-426614174002"),
    USER_UPDATE("123e4567-e89b-12d3-a456-426614174003"),
    USER_READ("123e4567-e89b-12d3-a456-426614174004"),
    USER_DELETE("123e4567-e89b-12d3-a456-426614174005"),
    PERMISSION_READ("123e4567-e89b-12d3-a456-426614174006"),
    ROLE_READ("123e4567-e89b-12d3-a456-426614174007"),
    ROLE_CREATE("123e4567-e89b-12d3-a456-426614174008"),
    ROLE_UPDATE("123e4567-e89b-12d3-a456-426614174009"),
    ROLE_DELETE("123e4567-e89b-12d3-a456-426614174010"),
    MANAGER_CREATE("123e4567-e89b-12d3-a456-426614174011"),
    MANAGER_UPDATE("123e4567-e89b-12d3-a456-426614174012"),
    MANAGER_READ("123e4567-e89b-12d3-a456-426614174013"),
    MANAGER_DELETE("123e4567-e89b-12d3-a456-426614174014");

    private final String uuid;

    PermissionName(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}