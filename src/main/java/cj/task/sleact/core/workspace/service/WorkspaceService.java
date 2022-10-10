package cj.task.sleact.core.workspace.service;

import cj.task.sleact.config.auth.dto.SessionUser;
import cj.task.sleact.core.workspace.component.ChannelComponent;
import cj.task.sleact.core.workspace.component.WorkspaceComponent;
import cj.task.sleact.core.workspace.controller.dto.request.CreateWorkspaceReq;
import cj.task.sleact.core.workspace.controller.dto.response.WorkspaceInfoRes;
import cj.task.sleact.core.workspace.controller.dto.response.WorkspaceMemberRes;
import cj.task.sleact.core.workspace.mapper.WorkspaceMapper;
import cj.task.sleact.entity.User;
import cj.task.sleact.entity.Workspace;
import cj.task.sleact.repository.UserRepository;
import cj.task.sleact.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceComponent workspaceComponent;
    private final ChannelComponent channelComponent;
    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public List<WorkspaceInfoRes> findWorkspacesBy(SessionUser user) {
        return workspaceRepository.findAllByMemberId(user.getId()).stream()
                .map(WorkspaceMapper.INSTANCE::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<WorkspaceMemberRes> findMembersInWorkspace(String workspaceUrl) {
        Workspace workspace = workspaceComponent.findWorkspaceByUrl(workspaceUrl);
        List<User> members = userRepository.findAllInWorkspace(workspace.getId());
        return WorkspaceMemberRes.fromEntity(members);
    }

    @Transactional(readOnly = true)
    public WorkspaceMemberRes findMemberInfo(String workspaceUrl, Long memberId) {
        Workspace workspace = workspaceComponent.findWorkspaceByUrl(workspaceUrl);
        User user = userRepository.findOneByUserIdAndWorkspaceId(memberId, workspace.getId())
                .orElseThrow(() -> new RuntimeException("해당 사용자는 존재하지 않습니다."));
        return WorkspaceMapper.INSTANCE.fromEntity(user);
    }

    @Transactional
    public WorkspaceInfoRes createWorkspaceWith(CreateWorkspaceReq request, Long userId) {
        workspaceRepository.findOneByUrl(request.getUrl())
                .ifPresent(x -> {
                    throw new RuntimeException("사용중인 워크스페이스 URL입니다.");
                });

        Workspace newWorkspace = workspaceComponent.createWorkspaceWith(request.getWorkspace(), request.getUrl(), userId);
        channelComponent.createChannelWith(newWorkspace, userId);

        return WorkspaceMapper.INSTANCE.fromEntity(newWorkspace);
    }

}
