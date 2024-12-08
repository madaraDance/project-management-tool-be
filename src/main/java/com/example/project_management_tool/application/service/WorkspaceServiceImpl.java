package com.example.project_management_tool.application.service;

import com.example.project_management_tool.application.dto.workspace.WorkspaceCreateDTO;
import com.example.project_management_tool.application.dto.workspace.WorkspaceMapper;
import com.example.project_management_tool.application.dto.workspace.WorkspaceUpdateDTO;
import com.example.project_management_tool.domain.model.Workspace;
import com.example.project_management_tool.domain.repository.company_abstraction.ICompanyRepository;
import com.example.project_management_tool.domain.repository.workspace_abstraction.IWorkspaceRepository;
import com.example.project_management_tool.domain.service.IWorkspaceService;
import com.example.project_management_tool.presentation.shared.error.CustomResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkspaceServiceImpl implements IWorkspaceService {

    private final IWorkspaceRepository iWorkspaceRepository;

    private final ICompanyRepository iCompanyRepository;

    private final WorkspaceMapper workspaceMapper;

    public WorkspaceServiceImpl(
            IWorkspaceRepository iWorkspaceRepository,
            ICompanyRepository iCompanyRepository,
            WorkspaceMapper workspaceMapper
    ) {
        this.iWorkspaceRepository = iWorkspaceRepository;
        this.iCompanyRepository = iCompanyRepository;
        this.workspaceMapper = workspaceMapper;
    }

    @Override
    public Workspace getOneById(UUID id) {
        return iWorkspaceRepository.findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Workspace with id: " + id + " was not found"));
    }

    @Override
    public List<Workspace> getAllByCompanyId(UUID companyId) {
        iCompanyRepository.findOneById(companyId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Company with id: " + companyId + " was not found."));
        return iWorkspaceRepository.findAllByCompanyId(companyId);
    }

    @Override
    public Workspace createWorkspace(UUID companyId, WorkspaceCreateDTO workspaceCreateDTO) {
        iCompanyRepository.findOneById(companyId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Company with id: " + companyId + " was not found."));
        return iWorkspaceRepository.save(workspaceMapper.mapCreateDtoToWorkspace(companyId, workspaceCreateDTO));

    }

    @Override
    public Workspace updateWorkspace(UUID id, WorkspaceUpdateDTO workspaceUpdateDTO) {
        Workspace workspaceFromDb = iWorkspaceRepository.findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Workspace with id: " + id + " was not found"));

        Workspace workspaceToSave = workspaceMapper.mapUpdateDtoToWorkspace(workspaceFromDb, workspaceUpdateDTO);

        return iWorkspaceRepository.save(workspaceToSave);
    }

    @Override
    public void deleteWorkspace(UUID id) {
        iWorkspaceRepository.findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Workspace with id: " + id + " was not found"));

        iWorkspaceRepository.deleteById(id);

    }
}
