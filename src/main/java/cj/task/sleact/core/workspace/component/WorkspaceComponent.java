package cj.task.sleact.core.workspace.component;

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
public class WorkspaceComponent {

    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    public Workspace findWorkspaceByUrl(String workspaceUrl) {
        return workspaceRepository.findOneByUrl(workspaceUrl)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 워크스페이스입니다."));
    }

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

    public void addUserToWorkspace(Workspace workspace, User user) {
        WorkspaceMember workspaceMember = WorkspaceMember.createBuilder()
                .workspace(workspace)
                .user(user)
                .build();

        workspace.getMembers().add(workspaceMember);
        user.getWorkspaces().add(workspaceMember);

        workspaceMemberRepository.save(workspaceMember);
    }

}
