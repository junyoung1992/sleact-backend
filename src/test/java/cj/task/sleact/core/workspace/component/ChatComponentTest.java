package cj.task.sleact.core.workspace.component;

import cj.task.sleact.entity.ChannelChat;
import cj.task.sleact.repository.ChannelChatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ChatComponentTest {

    @Mock
    ChannelChatRepository channelChatRepository;

    @InjectMocks
    ChatComponent chatComponent;

    @Nested
    @DisplayName("채팅 내역 조회")
    class FindPagingList {

        @Test
        @DisplayName("정상 조회")
        public void success() {
            // given
            Long channelId = 1L;
            Long perPage = 2L;
            Long page = 1L;

            ChannelChat chat1 = Mockito.mock(ChannelChat.class);
            given(chat1.getId()).willReturn(1L);
            given(chat1.getCreatedAt()).willReturn(LocalDateTime.now());

            ChannelChat chat2 = Mockito.mock(ChannelChat.class);
            given(chat2.getCreatedAt()).willReturn(LocalDateTime.now().minusDays(1));

            ChannelChat chat3 = Mockito.mock(ChannelChat.class);
            given(chat3.getId()).willReturn(3L);
            given(chat3.getCreatedAt()).willReturn(LocalDateTime.now().plusDays(1));

            List<ChannelChat> mockChats = List.of(chat1, chat2, chat3);
            given(channelChatRepository.findPagingAndFetchMemberByChannelId(anyLong())).willReturn(mockChats);

            // when
//            List<ChatInfoRes> result = chatComponent.findPagingList(channelId, perPage, page);

            // then
//            then(channelChatRepository).should(times(1)).findPagingAndFetchMemberByChannelId(channelId);

//            assertThat(result).extracting(ChatInfoRes::getId).containsExactly(3L, 1L);
        }

    }

}