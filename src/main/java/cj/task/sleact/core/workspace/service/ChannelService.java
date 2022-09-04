package cj.task.sleact.core.workspace.service;

import cj.task.sleact.core.workspace.controller.dto.request.CreateChannelReq;
import cj.task.sleact.core.workspace.controller.dto.response.ChannelInfoRes;
import cj.task.sleact.core.workspace.mapper.ChannelMapper;
import cj.task.sleact.core.workspace.service.subservice.ChannelRepositoryService;
import cj.task.sleact.entity.Workspace;
import cj.task.sleact.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final WorkspaceRepository workspaceRepository;
    private final ChannelRepositoryService channelRepositoryService;

    @Transactional(readOnly = true)
    public List<ChannelInfoRes> findChannelsBy(String workspaceUrl, Long userId) {
        Workspace findWorkspace = workspaceRepository.findOneByUrl(workspaceUrl)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 워크스페이스입니다."));

        return findWorkspace.getChannels().stream()
                .filter(c -> c.getMembers().stream()
                        .anyMatch(m -> Objects.equals(m.getMember().getId(), userId)))
                .map(ChannelMapper.INSTANCE::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChannelInfoRes findChannelInfoBy(String workspaceUrl, String channelName) {
        return workspaceRepository.findOneByUrl(workspaceUrl)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 워크스페이스입니다."))
                .getChannels().stream()
                .filter(c -> Objects.equals(c.getName(), channelName))
                .map(ChannelMapper.INSTANCE::fromEntity)
                .findAny()
                .orElseThrow(() -> new RuntimeException("존재하지 않는 채널입니다."));
    }


    @Transactional
    public ChannelInfoRes createChannelWith(CreateChannelReq request, String workspaceUrl, Long userId) {
        Workspace findWorkspace = workspaceRepository.findOneByUrl(workspaceUrl)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 워크스페이스입니다."));

        for (var channel : findWorkspace.getChannels()) {
            if (Objects.equals(channel.getName(), request.getName())) {
                throw new RuntimeException("이미 존재하는 채널 이름입니다.");
            }
        }

        return ChannelMapper.INSTANCE.fromEntity(
                channelRepositoryService.createChannelWith(findWorkspace, userId));
    }

}
