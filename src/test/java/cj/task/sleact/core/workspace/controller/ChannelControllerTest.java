package cj.task.sleact.core.workspace.controller;

import cj.task.sleact.core.workspace.controller.dto.request.CreateChannelReq;
import cj.task.sleact.core.workspace.controller.dto.response.ChannelInfoRes;
import cj.task.sleact.core.workspace.service.ChannelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static cj.task.sleact.common.constants.ApiUrlConstants.Workspace.A_CHANNEL;
import static cj.task.sleact.common.constants.ApiUrlConstants.Workspace.BASE_URL;
import static cj.task.sleact.common.constants.ApiUrlConstants.Workspace.CHANNELS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChannelController.class)
class ChannelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ChannelService channelService;

    @Test
    @WithMockUser
    @DisplayName("참가한 채널 조회")
    public void getChannelsByWorkspaceAndUserId() throws Exception {
        // given
        Long workspaceId = 1L;
        Long channelId = 1L;
        String channelName = "channel_test";
        boolean privates = false;

        ChannelInfoRes response = new ChannelInfoRes(channelId, channelName, privates, workspaceId);

        given(channelService.findChannelsBy(anyString(), anyLong())).willReturn(List.of(response));

        // when
        ResultActions result = mockMvc.perform(get(BASE_URL + CHANNELS, workspaceId));

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(channelId))
                .andExpect(jsonPath("$[0].name").value(channelName))
                .andExpect(jsonPath("$[0].private").value(privates))
                .andExpect(jsonPath("$[0].workspaceId").value(workspaceId));

        // verify
        then(channelService).should(times(1)).findChannelsBy(anyString(), anyLong());
    }

    @Test
    @WithMockUser
    @DisplayName("채널 생성")
    public void createChannel() throws Exception {
        // given
        Long workspaceId = 1L;
        String channelName = "테스트";
        Long channelId = 1L;
        boolean privates = false;

        CreateChannelReq request = CreateChannelReq.builder()
                .name(channelName)
                .build();

        ChannelInfoRes response = ChannelInfoRes.builder()
                .id(channelId)
                .name(channelName)
                .privates(privates)
                .workspaceId(workspaceId)
                .build();

        String requestJson = mapper.writeValueAsString(request);

        given(channelService.createChannelWith(any(CreateChannelReq.class), anyString(), anyLong())).willReturn(response);

        // when
        ResultActions result = mockMvc.perform(post(BASE_URL + CHANNELS, workspaceId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(channelId))
                .andExpect(jsonPath("$.name").value(channelName))
                .andExpect(jsonPath("$.private").value(privates))
                .andExpect(jsonPath("$.workspaceId").value(workspaceId));

        // verify
        then(channelService).should(times(1)).createChannelWith(any(CreateChannelReq.class), anyString(), anyLong());
    }

    @Test
    @WithMockUser
    @DisplayName("채널 정보 조회")
    public void getChannelInfo() throws Exception {
        // given
        Long workspaceId = 1L;
        String channelName = "테스트";
        Long channelId = 1L;
        boolean privates = false;

        ChannelInfoRes response = new ChannelInfoRes(channelId, channelName, privates, workspaceId);

        given(channelService.findChannelInfoBy(anyString(), anyString())).willReturn(response);

        // when
        ResultActions result = mockMvc.perform(get(BASE_URL + A_CHANNEL, workspaceId, channelName));

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(channelId))
                .andExpect(jsonPath("$.name").value(channelName))
                .andExpect(jsonPath("$.private").value(privates))
                .andExpect(jsonPath("$.workspaceId").value(workspaceId));

        // verify
        then(channelService).should(times(1)).findChannelInfoBy(anyString(), anyString());
    }

}