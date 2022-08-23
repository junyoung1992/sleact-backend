package cj.task.sleact.core.channel.repositoryservice;

import cj.task.sleact.persistence.entity.Channel;
import cj.task.sleact.persistence.entity.ChannelMember;
import cj.task.sleact.persistence.entity.User;
import cj.task.sleact.persistence.entity.Workspace;
import cj.task.sleact.persistence.repository.ChannelMemberRepository;
import cj.task.sleact.persistence.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelRepositoryService {

    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;

    public Channel createChannelWith(User owner, Workspace newWorkspace) {
        Channel newChannel = Channel.createBuilder()
                .workspace(newWorkspace)
                .name("일반")
                .build();

        ChannelMember newChannelMember = ChannelMember.createBuilder()
                .channel(newChannel)
                .user(owner)
                .build();

        channelRepository.save(newChannel);
        channelMemberRepository.save(newChannelMember);

        return newChannel;
    }

}
