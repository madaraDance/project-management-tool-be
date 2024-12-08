package com.example.project_management_tool.presentation.shared;


import lombok.Getter;

import java.util.List;

@Getter
public class GlobalResponse <T> {
    private final static String ERROR = "error";

    private final static String SUCCESS = "success";

    private final String status;

    private final int code;

    private final T data;

    private final List<ErrorItem> errors;

    public GlobalResponse(int code, List<ErrorItem> errors) {
        this.status = ERROR;
        this.code = code;
        this.errors = errors;
        this.data = null;
    }

    public GlobalResponse(int code, T data) {
        this.status = SUCCESS;
        this.code = code;
        this.errors = null;
        this.data = data;
    }

    @Getter
    public static class ErrorItem {
        private final String message;

        public ErrorItem(String message) {
            this.message = message;
        }
    }
}
