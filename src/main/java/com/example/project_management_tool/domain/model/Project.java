package com.example.project_management_tool.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "project"
)
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseEntity{

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(
            name = "name"
    )
    @NotBlank
    private String name;

    @Column(
            name = "description"
    )
    private String description;

    @Column(
            name = "start_date"
    )
    private Instant startDate;

    @Column(
            name = "end_date"
    )
    private Instant endDate;

    @Column(
            name = "workspace_id"
    )
    @NotNull
    private UUID workspaceId;
}
