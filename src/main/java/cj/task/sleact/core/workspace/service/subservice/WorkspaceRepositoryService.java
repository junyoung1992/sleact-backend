package cj.task.sleact.core.workspace.service.subservice;

import cj.task.sleact.entity.User;
import cj.task.sleact.entity.Workspace;
import cj.task.sleact.entity.WorkspaceMember;
import cj.task.sleact.repository.UserRepository;
import cj.task.sleact.repository.WorkspaceMemberRepository;
import cj.task.sleact.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkspaceRepositoryService {

    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    public Workspace createWorkspaceWith(String workspaceName, String url, Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

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
