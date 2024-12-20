package com.example.project_management_tool.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(
    name = "role",
    uniqueConstraints = @UniqueConstraint(
            name = "uc_company_role_name",
            columnNames = {"company_id", "name"})
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(
        name = "name",
        nullable = false)
    @Size(
            max = 40,
            message = "Name must be at most 40 characters")
    @NotBlank
    private String name;

    @Column(
            name = "is_System_Role",
            columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isSystemRole;

    @Column(
            name = "company_id",
            nullable = false)
    @NotNull
    private UUID companyId;

}
