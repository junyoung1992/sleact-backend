package cj.task.sleact.core.channel.service;

import cj.task.sleact.core.channel.component.ChannelComponent;
import cj.task.sleact.core.channel.controller.dto.request.CreateChannelReq;
import cj.task.sleact.core.channel.controller.dto.response.ChannelInfoRes;
import cj.task.sleact.core.channel.controller.dto.response.ChannelMemberRes;
import cj.task.sleact.core.channel.mapper.ChannelMapper;
import cj.task.sleact.core.workspace.component.WorkspaceComponent;
import cj.task.sleact.entity.Channel;
import cj.task.sleact.entity.User;
import cj.task.sleact.entity.Workspace;
import cj.task.sleact.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ChannelInfoRes> findChannelsBy(String workspaceUrl, Long userId) {
        Workspace findWorkspace = workspaceComponent.findWorkspaceByUrl(workspaceUrl);

        return findWorkspace.getChannels().stream()
                .filter(c -> c.getMembers().stream()
                        .anyMatch(m -> Objects.equals(m.getUser().getId(), userId)))
                .map(ChannelMapper.INSTANCE::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChannelInfoRes findChannelInfoBy(String workspaceUrl, String channelName) {
        workspaceComponent.findWorkspaceByUrl(workspaceUrl);
        Channel channel = channelComponent.findByWorkspaceUrlAndChannelName(workspaceUrl, channelName);
        return ChannelMapper.INSTANCE.fromEntity(channel);
    }

    @Transactional(readOnly = true)
    public List<ChannelMemberRes> findMembersInChannel(String workspaceUrl, String channelName) {
        workspaceComponent.findWorkspaceByUrl(workspaceUrl);
        Channel channel = channelComponent.findByWorkspaceUrlAndChannelName(workspaceUrl, channelName);

        List<User> members = userRepository.findAllInChannel(channel.getId());
        return ChannelMemberRes.fromEntity(members);
    }

    @Transactional
    public ChannelInfoRes createChannelWith(CreateChannelReq request, String workspaceUrl, Long userId) {
        Workspace findWorkspace = workspaceComponent.findWorkspaceByUrl(workspaceUrl);

        for (var channel : findWorkspace.getChannels()) {
            if (Objects.equals(channel.getName(), request.getName())) {
                throw new RuntimeException("?????? ???????????? ?????? ???????????????.");
            }
        }

        return ChannelMapper.INSTANCE.fromEntity(
                channelComponent.createChannelWith(findWorkspace, userId, request.getName()));
    }

    @Transactional
    public void inviteMember(String workspaceUrl, String channelName, String email) {
        Workspace workspace = workspaceComponent.findWorkspaceByUrl(workspaceUrl);
        Channel channel = channelComponent.findByWorkspaceUrlAndChannelName(workspaceUrl, channelName);
        User user = userRepository.findOneByEmailAndWorkspaceId(email, workspace.getId())
                .orElseThrow(() -> new RuntimeException("???????????? ?????? ??????????????????."));

        channelComponent.addUserToChannel(channel, user);
    }

}
