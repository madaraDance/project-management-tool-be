package com.example.project_management_tool.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(
        name = "role_permission",
        uniqueConstraints = @UniqueConstraint(
                name = "uc_role_permission",
                columnNames = {"role_id", "permission_id"})
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RolePermission extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotNull
    @Column(name = "role_id")
    private UUID roleId;

    @NotNull
    @Column(name = "permission_id")
    private UUID permissionId;

}
