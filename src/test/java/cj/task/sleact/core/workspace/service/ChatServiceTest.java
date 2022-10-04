package cj.task.sleact.core.workspace.service;

import cj.task.sleact.core.workspace.component.ChannelComponent;
import cj.task.sleact.core.workspace.component.ChatComponent;
import cj.task.sleact.core.workspace.component.WorkspaceComponent;
import cj.task.sleact.core.workspace.controller.dto.response.ChatInfoRes;
import cj.task.sleact.entity.Channel;
import cj.task.sleact.entity.Workspace;
import cj.task.sleact.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Mock
    MemberRepository memberRepository;

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

            Workspace mockWorkspace = Mockito.mock(Workspace.class);
            Channel mockChannel = Mockito.mock(Channel.class);
            given(mockChannel.getId()).willReturn(channelId);

            given(workspaceComponent.findWorkspaceByUrl(anyString())).willReturn(mockWorkspace);
            given(channelComponent.findByWorkspaceUrlAndChannelName(anyString(), anyString())).willReturn(mockChannel);

            ChatInfoRes chat1 = Mockito.mock(ChatInfoRes.class);
            ChatInfoRes chat2 = Mockito.mock(ChatInfoRes.class);
            List<ChatInfoRes> mockChats = List.of(chat1, chat2);
            given(chatComponent.findPagingList(anyLong(), anyLong(), anyLong())).willReturn(mockChats);

            // when
            List<ChatInfoRes> result = chatService.findPagingList(workspaceUrl, channelName, perPage, page);

            // then
            then(workspaceComponent).should(times(1)).findWorkspaceByUrl(workspaceUrl);
            then(channelComponent).should(times(1)).findByWorkspaceUrlAndChannelName(workspaceUrl, channelName);
            then(chatComponent).should(times(1)).findPagingList(channelId, perPage, page);

            assertThat(result).containsExactly(chat1, chat2);
        }

    }

}