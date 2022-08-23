package cj.task.sleact.core.workspace.controller;

import cj.task.sleact.common.constants.ApiUrlConstants;
import cj.task.sleact.core.workspace.dto.request.CreateWorkspaceHttpReq;
import cj.task.sleact.core.workspace.dto.response.WorkspaceInfoRes;
import cj.task.sleact.core.workspace.mapper.WorkspaceMapper;
import cj.task.sleact.core.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = ApiUrlConstants.Workspace.BASE_URL)
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping
    public List<WorkspaceInfoRes> getWorkspaceInfoByUserId() {
        Long userId = 1L;
        return workspaceService.findWorkspaceInfoByUserId(userId);
    }

    @PostMapping
    public WorkspaceInfoRes createWorkspace(@RequestBody CreateWorkspaceHttpReq body) {
        Long userId = 1L;
        return workspaceService.createWorkspaceWith(
                WorkspaceMapper.INSTANCE.fromHttpReq(body, userId));
    }

}
