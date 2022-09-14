package cj.task.sleact.core.workspace.component;

import cj.task.sleact.entity.Channel;
import cj.task.sleact.entity.ChannelMember;
import cj.task.sleact.entity.Member;
import cj.task.sleact.entity.Workspace;
import cj.task.sleact.repository.ChannelMemberRepository;
import cj.task.sleact.repository.ChannelRepository;
import cj.task.sleact.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelComponent {

    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;
    private final ChannelMemberRepository channelMemberRepository;

    public Channel findByWorkspaceUrlAndChannelName(String workspaceUrl, String channelName) {
        return channelRepository.findByNameAndWorkspaceUrl(channelName, workspaceUrl)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 채널입니다."));
    }

    public Channel createChannelWith(Workspace workspace, Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        Channel newChannel = Channel.createBuilder()
                .workspace(workspace)
                .name("일반")
                .build();

        ChannelMember newChannelMember = ChannelMember.createBuilder()
                .channel(newChannel)
                .member(member)
                .build();

        channelRepository.save(newChannel);
        channelMemberRepository.save(newChannelMember);

        return newChannel;
    }

}
