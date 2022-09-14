package cj.task.sleact.core.workspace.service;

import cj.task.sleact.core.workspace.component.ChannelComponent;
import cj.task.sleact.core.workspace.component.WorkspaceComponent;
import cj.task.sleact.core.workspace.controller.dto.request.CreateChannelReq;
import cj.task.sleact.core.workspace.controller.dto.response.ChannelInfoRes;
import cj.task.sleact.core.workspace.mapper.ChannelMapper;
import cj.task.sleact.entity.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final WorkspaceComponent workspaceComponent;
    private final ChannelComponent channelComponent;

    @Transactional(readOnly = true)
    public List<ChannelInfoRes> findChannelsBy(String workspaceUrl, Long userId) {
        Workspace findWorkspace = workspaceComponent.findWorkspaceByUrl(workspaceUrl);

        return findWorkspace.getChannels().stream()
                .filter(c -> c.getMembers().stream()
                        .anyMatch(m -> Objects.equals(m.getMember().getId(), userId)))
                .map(ChannelMapper.INSTANCE::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChannelInfoRes findChannelInfoBy(String workspaceUrl, String channelName) {
        workspaceComponent.findWorkspaceByUrl(workspaceUrl);
        return ChannelMapper.INSTANCE.fromEntity(
                channelComponent.findByWorkspaceUrlAndChannelName(workspaceUrl, channelName));
    }


    @Transactional
    public ChannelInfoRes createChannelWith(CreateChannelReq request, String workspaceUrl, Long userId) {
        Workspace findWorkspace = workspaceComponent.findWorkspaceByUrl(workspaceUrl);

        for (var channel : findWorkspace.getChannels()) {
            if (Objects.equals(channel.getName(), request.getName())) {
                throw new RuntimeException("이미 존재하는 채널 이름입니다.");
            }
        }

        return ChannelMapper.INSTANCE.fromEntity(
                channelComponent.createChannelWith(findWorkspace, userId));
    }

}
