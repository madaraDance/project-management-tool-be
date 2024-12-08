package com.example.project_management_tool.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity()
@Table(name = "workspace", uniqueConstraints = @UniqueConstraint(
        name = "uc_company_workspace_name",
        columnNames = {"name", "company_id"}
))
@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
public class Workspace extends BaseEntity{

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotBlank
    @Column(
            name = "name"
    )
    private String name;

    @Column(
            name = "description",
            columnDefinition = "TEXT"
    )
    private String description;

    @NotNull
    @Column(
            name = "company_id"
    )
    private UUID companyId;
}
