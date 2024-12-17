package com.example.project_management_tool.application.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class ProjectCreateDTO {

    @NotBlank
    private String name;

    private String description;

    private Instant startDate;

    private Instant endDate;

    @NotNull
    private UUID workspaceId;
}
