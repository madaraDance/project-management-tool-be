package com.example.project_management_tool.application.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class ProjectUpdateDTO {

    private String name;

    private String description;

    private Instant startDate;

    private Instant endDate;
}
