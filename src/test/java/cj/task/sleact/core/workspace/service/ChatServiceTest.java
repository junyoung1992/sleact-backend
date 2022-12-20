package cj.task.sleact.core.workspace.service;

import cj.task.sleact.core.channel.component.ChannelComponent;
import cj.task.sleact.core.chat.component.ChatComponent;
import cj.task.sleact.core.chat.service.ChatService;
import cj.task.sleact.core.workspace.component.WorkspaceComponent;
import cj.task.sleact.entity.Channel;
import cj.task.sleact.entity.ChannelChat;
import cj.task.sleact.entity.User;
import cj.task.sleact.entity.Workspace;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    WorkspaceComponent workspaceComponent;

    @Mock
    ChannelComponent channelComponent;

    @Mock
    ChatComponent chatComponent;

    @InjectMocks
    ChatService chatService;

    @Nested
    @DisplayName("채팅 내역 조회")
    class FindPagingList {

        @Test
        @DisplayName("정상 조회")
        public void success() {
            // given
            String workspaceUrl = "wks1";
            String channelName = "ch1";
            Long channelId = 1L;
            Long perPage = 20L;
            Long page = 1L;

            Long userId1 = 1L;
            String username1 = "user1";
            Long userId2 = 2L;
            String username2 = "user2";

            Workspace mockWorkspace = Mockito.mock(Workspace.class);
            Channel mockChannel = Mockito.mock(Channel.class);
            given(mockChannel.getId()).willReturn(channelId);
            given(mockChannel.getName()).willReturn(channelName);

            User mockUser1 = Mockito.mock(User.class);
            User mockUser2 = Mockito.mock(User.class);
            given(mockUser1.getId()).willReturn(userId1);
            given(mockUser1.getName()).willReturn(username1);
            given(mockUser2.getId()).willReturn(userId2);
            given(mockUser2.getName()).willReturn(username2);

            ChannelChat chat1 = Mockito.mock(ChannelChat.class);
            ChannelChat chat2 = Mockito.mock(ChannelChat.class);
            List<ChannelChat> mockChats = List.of(chat1, chat2);

            given(chat1.getId()).willReturn(1L);
            given(chat1.getChannel()).willReturn(mockChannel);
            given(chat1.getUser()).willReturn(mockUser1);
            given(chat2.getId()).willReturn(2L);
            given(chat2.getChannel()).willReturn(mockChannel);
            given(chat2.getUser()).willReturn(mockUser2);

            given(workspaceComponent.findWorkspaceByUrl(anyString())).willReturn(mockWorkspace);
            given(channelComponent.findByWorkspaceUrlAndChannelName(anyString(), anyString())).willReturn(mockChannel);
            given(chatComponent.findPagingList(anyLong(), anyLong(), anyLong())).willReturn(mockChats);

            // when
            chatService.findPagingList(workspaceUrl, channelName, perPage, page);

            // then
            then(workspaceComponent).should(times(1)).findWorkspaceByUrl(workspaceUrl);
            then(channelComponent).should(times(1)).findByWorkspaceUrlAndChannelName(workspaceUrl, channelName);
            then(chatComponent).should(times(1)).findPagingList(channelId, perPage, page);
        }

    }

}