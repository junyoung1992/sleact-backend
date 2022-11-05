package cj.task.sleact.core.channel.component;

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
public class ChannelComponent {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;

    public Channel findByWorkspaceUrlAndChannelName(String workspaceUrl, String channelName) {
        return channelRepository.findByNameAndWorkspaceUrl(channelName, workspaceUrl)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 채널입니다."));
    }

    public Channel createChannelWith(Workspace workspace, Long userId, String channelName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        Channel newChannel = Channel.createBuilder()
                .workspace(workspace)
                .name(channelName)
                .build();

        ChannelMember newChannelMember = ChannelMember.createBuilder()
                .channel(newChannel)
                .user(user)
                .build();

        channelRepository.save(newChannel);
        channelMemberRepository.save(newChannelMember);

        return newChannel;
    }

    public void addUserToChannel(Channel channel, User user) {
        ChannelMember channelMember = ChannelMember.createBuilder()
                .channel(channel)
                .user(user)
                .build();

        channel.getMembers().add(channelMember);
        user.getChannels().add(channelMember);

        channelMemberRepository.save(channelMember);
    }

}
