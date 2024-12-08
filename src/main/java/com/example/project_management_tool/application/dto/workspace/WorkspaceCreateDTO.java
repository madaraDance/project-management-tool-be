package com.example.project_management_tool.application.dto.workspace;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceCreateDTO {

    @NotBlank
    private String name;

    private String description;
}
