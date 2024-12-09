package com.example.project_management_tool.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(
        name = "workspace_user_role",
        uniqueConstraints = @UniqueConstraint(
                name = "uc_workspace_user",
                columnNames = {"workspace_id", "user_id"}
        ))
@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
public class WorkspaceUserRole extends BaseEntity{

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotNull
    @Column(name = "workspace_id")
    private UUID workspaceId;

    @NotNull
    @Column(name = "user_id")
    private UUID userId;

    @NotNull
    @Column(name = "role_id")
    private UUID roleId;
}
