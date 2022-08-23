package cj.task.sleact.core.workspace.repositoryservice;

import cj.task.sleact.persistence.entity.User;
import cj.task.sleact.persistence.entity.Workspace;
import cj.task.sleact.persistence.entity.WorkspaceMember;
import cj.task.sleact.persistence.repository.WorkspaceMemberRepository;
import cj.task.sleact.persistence.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkspaceRepositoryService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    public Workspace createWorkspaceWith(String workspaceName, String url, User owner) {
        Workspace newWorkspace = Workspace.createBuilder()
                .name(workspaceName)
                .url(url)
                .owner(owner)
                .build();

        WorkspaceMember newWorkspaceMember = WorkspaceMember.createBuilder()
                .workspace(newWorkspace)
                .user(owner)
                .build();

        workspaceRepository.save(newWorkspace);
        workspaceMemberRepository.save(newWorkspaceMember);
        return newWorkspace;
    }

}
