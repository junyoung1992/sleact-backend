package cj.task.sleact.restdocs.core.workspace;

import cj.task.sleact.common.constants.ApiUrlConstants;
import cj.task.sleact.core.workspace.controller.WorkspaceController;
import cj.task.sleact.core.workspace.dto.response.WorkspaceInfoRes;
import cj.task.sleact.core.workspace.service.WorkspaceService;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
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

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.Schema.schema;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
                .andExpect(jsonPath("$[0].ownerId").value(ownerId))
                .andDo(document(
                        "Workspace 정상 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("Workspace 조회")
                                .description("User가 속한 Workspace 조회")
                                .tag("Workspace")
                                .responseSchema(schema("WorkspaceInfoRes"))
                                .responseFields(
                                        fieldWithPath("[].name").type(SimpleType.STRING).description("워크스페이스 이름"),
                                        fieldWithPath("[].url").type(SimpleType.STRING).description("워크스페이스 주소"),
                                        fieldWithPath("[].ownerId").type(SimpleType.NUMBER).description("워크스페이스 생성자 ID")
                                )
                                .build())));
    }

}