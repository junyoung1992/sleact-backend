package cj.task.sleact.core.workspace.service;

import cj.task.sleact.core.workspace.component.ChannelComponent;
import cj.task.sleact.core.workspace.component.ChatComponent;
import cj.task.sleact.core.workspace.component.WorkspaceComponent;
import cj.task.sleact.core.workspace.controller.dto.response.ChatInfoRes;
import cj.task.sleact.entity.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final WorkspaceComponent workspaceComponent;
    private final ChannelComponent channelComponent;
    private final ChatComponent chatComponent;

    @Transactional(readOnly = true)
    public List<ChatInfoRes> findPagingList(String workspaceUrl, String channelName, Long perPage, Long page) {
        workspaceComponent.findWorkspaceByUrl(workspaceUrl);
        Channel channel = channelComponent.findByWorkspaceUrlAndChannelName(workspaceUrl, channelName);

        return chatComponent.findPagingList(channel.getId(), perPage, page);
    }

}
