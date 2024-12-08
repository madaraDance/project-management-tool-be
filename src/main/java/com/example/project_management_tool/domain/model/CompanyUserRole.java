package com.example.project_management_tool.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;


@Entity
@Table(
    name = "company_user_role",
    uniqueConstraints = {@UniqueConstraint(
        name = "uc_company_user",
        columnNames = {"company_id", "user_id"})
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyUserRole extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "company_id",
    nullable = false)
    @NotNull
    private UUID companyId;

    @Column(name = "user_id",
            nullable = false)
    @NotNull
    private UUID userId;

    @Column(name = "role_id",
            nullable = false)
    @NotNull
    private UUID roleId;
}
