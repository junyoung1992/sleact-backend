package cj.task.sleact.core.workspace.service;

import cj.task.sleact.core.workspace.component.ChannelComponent;
import cj.task.sleact.core.workspace.component.ChatComponent;
import cj.task.sleact.core.workspace.component.WorkspaceComponent;
import cj.task.sleact.core.workspace.controller.dto.request.PostChatReq;
import cj.task.sleact.core.workspace.controller.dto.response.ChatInfoRes;
import cj.task.sleact.entity.Channel;
import cj.task.sleact.entity.User;
import cj.task.sleact.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final WorkspaceComponent workspaceComponent;
    private final ChannelComponent channelComponent;
    private final ChatComponent chatComponent;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ChatInfoRes> findPagingList(String workspaceUrl, String channelName, Long perPage, Long page) {
        Channel channel = findChannel(workspaceUrl, channelName);
        return chatComponent.findPagingList(channel.getId(), perPage, page);
    }

    @Transactional(readOnly = true)
    public Long countUnreadChat(String workspaceUrl, String channelName, LocalDateTime after) {
        Channel channel = findChannel(workspaceUrl, channelName);
        return chatComponent.countChannelChatAfterTarget(channel.getId(), after);
    }

    @Transactional
    public void post(String workspaceUrl, String channelName, Long memberId, PostChatReq request) {
        Channel channel = findChannel(workspaceUrl, channelName);

        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
        chatComponent.post(channel, user, request.getContent());
    }

    private Channel findChannel(String workspaceUrl, String channelName) {
        workspaceComponent.findWorkspaceByUrl(workspaceUrl);
        return channelComponent.findByWorkspaceUrlAndChannelName(workspaceUrl, channelName);
    }

}
