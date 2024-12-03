package com.example.project_management_tool.application.dto.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class CompanyUpdateDTO {
    private String companyName;

    private String description;
}
