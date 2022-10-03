package cj.task.sleact.restdocs.core;

import cj.task.sleact.core.workspace.controller.dto.response.ChatInfoRes;
import cj.task.sleact.core.workspace.service.ChatService;
import cj.task.sleact.core.workspace.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static cj.task.sleact.common.constants.ApiUrlConstants.Workspace.BASE_URL;
import static cj.task.sleact.common.constants.ApiUrlConstants.Workspace.CHAT;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureRestDocs
@WebMvcTest(ChatControllerTest.class)
public class ChatControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ChatService chatService;

    @MockBean
    UploadService uploadService;

    @Nested
    @DisplayName("채팅 내역 조회")
    class GetChatList {

        @Test
        @WithMockUser
        @DisplayName("채팅 내역 정상 조회")
        public void success() throws Exception {
            // given
            ChatInfoRes chatInfo1 = ChatInfoRes.builder()
                    .id(3L)
                    .userId(1L)
                    .user(null)
                    .content("채팅")
                    .createdAt(LocalDateTime.now())
                    .channelId(2L)
                    .channel(null)
                    .build();

            ChatInfoRes chatInfo2 = ChatInfoRes.builder()
                    .id(5L)
                    .userId(4L)
                    .user(null)
                    .content("채팅")
                    .createdAt(LocalDateTime.now())
                    .channelId(2L)
                    .channel(null)
                    .build();

            List<ChatInfoRes> mockChats = List.of(chatInfo1, chatInfo2);
            given(chatService.findPagingList(anyString(), anyString(), anyLong(), anyLong())).willReturn(mockChats);

            // when
            ResultActions result = mockMvc.perform(get(BASE_URL + CHAT, "wks1", "2")
                    .param("perPage", "20")
                    .param("page", "1"));

            // then
            then(chatService).should(times(1)).findPagingList(anyString(), anyString(), anyLong(), anyLong());

            result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(chatInfo1.getId()))
                    .andExpect(jsonPath("$[0].userId").value(chatInfo1.getUserId()))
                    .andExpect(jsonPath("$[0].content").value(chatInfo1.getContent()))
                    .andExpect(jsonPath("$[0].createdAt").value(chatInfo1.getCreatedAt()))
                    .andExpect(jsonPath("$[0].channelId").value(chatInfo1.getChannelId()))
                    .andExpect(jsonPath("$[1].id").value(chatInfo2.getId()))
                    .andExpect(jsonPath("$[1].userId").value(chatInfo2.getUserId()))
                    .andExpect(jsonPath("$[1].content").value(chatInfo2.getContent()))
                    .andExpect(jsonPath("$[1].createdAt").value(chatInfo2.getCreatedAt()))
                    .andExpect(jsonPath("$[1].channelId").value(chatInfo2.getChannelId()));
        }
    }

}
