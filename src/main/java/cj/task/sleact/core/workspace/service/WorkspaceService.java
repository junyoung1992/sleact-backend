package cj.task.sleact.core.workspace.service;

import cj.task.sleact.core.channel.repositoryservice.ChannelRepositoryService;
import cj.task.sleact.core.workspace.dto.request.CreateWorkspaceReq;
import cj.task.sleact.core.workspace.dto.response.WorkspaceInfoRes;
import cj.task.sleact.core.workspace.mapper.WorkspaceMapper;
import cj.task.sleact.core.workspace.repositoryservice.WorkspaceRepositoryService;
import cj.task.sleact.persistence.entity.Channel;
import cj.task.sleact.persistence.entity.User;
import cj.task.sleact.persistence.entity.Workspace;
import cj.task.sleact.persistence.repository.UserRepository;
import cj.task.sleact.persistence.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final WorkspaceRepositoryService workspaceRepositoryService;
    private final ChannelRepositoryService channelRepositoryService;


    @Transactional(readOnly = true)
    public List<WorkspaceInfoRes> findWorkspaceInfoByUserId(Long userId) {
        return workspaceRepository.findAllByMemberId(userId).stream()
                .map(WorkspaceMapper.INSTANCE::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public WorkspaceInfoRes createWorkspaceWith(CreateWorkspaceReq request) {
        workspaceRepository.findOneByUrl(request.getUrl())
                .ifPresent(x -> {
                    throw new RuntimeException("사용중인 워크스페이스 URL입니다.");
                });

        User owner = userRepository.findById(request.getUserId())
                .orElseThrow();

        Workspace newWorkspace = workspaceRepositoryService.createWorkspaceWith(request.getWorkspace(), request.getUrl(), owner);
        Channel newChannel = channelRepositoryService.createChannelWith(owner, newWorkspace);

        return WorkspaceMapper.INSTANCE.fromEntity(newWorkspace);
    }

}
