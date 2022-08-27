package cj.task.sleact.core.workspace.service;

import cj.task.sleact.core.workspace.controller.dto.request.CreateWorkspaceReq;
import cj.task.sleact.core.workspace.controller.dto.response.WorkspaceInfoRes;
import cj.task.sleact.core.workspace.mapper.WorkspaceMapper;
import cj.task.sleact.core.workspace.service.subservice.ChannelRepositoryService;
import cj.task.sleact.core.workspace.service.subservice.WorkspaceRepositoryService;
import cj.task.sleact.entity.Workspace;
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
    private final WorkspaceRepositoryService workspaceRepositoryService;
    private final ChannelRepositoryService channelRepositoryService;


    @Transactional(readOnly = true)
    public List<WorkspaceInfoRes> findWorkspacesBy(Long userId) {
        return workspaceRepository.findAllByMemberId(userId).stream()
                .map(WorkspaceMapper.INSTANCE::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public WorkspaceInfoRes createWorkspaceWith(CreateWorkspaceReq request, Long userId) {
        workspaceRepository.findOneByUrl(request.getUrl())
                .ifPresent(x -> {
                    throw new RuntimeException("사용중인 워크스페이스 URL입니다.");
                });

        Workspace newWorkspace = workspaceRepositoryService.createWorkspaceWith(request.getWorkspace(), request.getUrl(), userId);
        channelRepositoryService.createChannelWith(newWorkspace, userId);

        return WorkspaceMapper.INSTANCE.fromEntity(newWorkspace);
    }

}
