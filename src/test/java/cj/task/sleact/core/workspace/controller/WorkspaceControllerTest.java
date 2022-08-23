package cj.task.sleact.core.workspace.controller;

import cj.task.sleact.common.constants.ApiUrlConstants;
import cj.task.sleact.core.workspace.dto.response.WorkspaceInfoRes;
import cj.task.sleact.core.workspace.service.WorkspaceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(WorkspaceController.class)
class WorkspaceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WorkspaceService workspaceService;

    @Test
    @WithMockUser
    @DisplayName("User가 속한 Workspace 조회")
    public void getWorkspaceInfoByUserId() throws Exception {
        // given
        Long ownerId = 1L;
        String name = "workspace_test";
        String url = "test.workspace.com";
        WorkspaceInfoRes response = new WorkspaceInfoRes(name, url, ownerId);
        given(workspaceService.findWorkspaceInfoByUserId(anyLong())).willReturn(List.of(response));

        // when
        ResultActions result = mockMvc.perform(get(ApiUrlConstants.Workspace.BASE_URL));

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].url").value(url))
                .andExpect(jsonPath("$[0].ownerId").value(ownerId));

        // verify
        then(workspaceService).should(times(1)).findWorkspaceInfoByUserId(anyLong());
    }

}