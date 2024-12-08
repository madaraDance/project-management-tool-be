package com.example.project_management_tool.domain.enums;

public enum SystemRole {
    OWNER("123e4567-e89b-12d3-a456-426614000000"),
    ADMIN("123e4567-e89b-12d3-a456-426614000001");

    private final String uuid;

    SystemRole(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
