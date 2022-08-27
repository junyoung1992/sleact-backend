package cj.task.sleact.core.workspace.service.subservice;

import cj.task.sleact.entity.Channel;
import cj.task.sleact.entity.ChannelMember;
import cj.task.sleact.entity.User;
import cj.task.sleact.entity.Workspace;
import cj.task.sleact.repository.ChannelMemberRepository;
import cj.task.sleact.repository.ChannelRepository;
import cj.task.sleact.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelRepositoryService {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;

    public Channel createChannelWith(Workspace workspace, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        Channel newChannel = Channel.createBuilder()
                .workspace(workspace)
                .name("일반")
                .build();

        ChannelMember newChannelMember = ChannelMember.createBuilder()
                .channel(newChannel)
                .user(user)
                .build();

        channelRepository.save(newChannel);
        channelMemberRepository.save(newChannelMember);

        return newChannel;
    }

}
