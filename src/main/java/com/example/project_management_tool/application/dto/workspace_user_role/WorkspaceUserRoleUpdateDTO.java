package com.example.project_management_tool.application.dto.workspace_user_role;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceUserRoleUpdateDTO {

    @NotNull
    private UUID roleId;

}
