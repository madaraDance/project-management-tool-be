package com.example.project_management_tool.application.dto.workspace_user_role;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceUserRoleReadDTO {

    private UUID id;

    private String workspaceName;

    private String userFirstName;

    private String userLastName;

    private String roleName;

}