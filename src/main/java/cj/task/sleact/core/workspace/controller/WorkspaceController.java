package cj.task.sleact.core.workspace.controller;

import cj.task.sleact.common.constants.ApiUrlConstants;
import cj.task.sleact.core.workspace.controller.dto.request.CreateWorkspaceReq;
import cj.task.sleact.core.workspace.controller.dto.response.WorkspaceInfoRes;
import cj.task.sleact.core.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = ApiUrlConstants.Workspace.BASE_URL)
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping
    public List<WorkspaceInfoRes> getWorkspacesByUserId() {
        Long userId = 1L;
        return workspaceService.findWorkspacesBy(userId);
    }

    @PostMapping
    public WorkspaceInfoRes createWorkspace(@RequestBody @Valid CreateWorkspaceReq body) {
        Long userId = 1L;
        return workspaceService.createWorkspaceWith(body, userId);
    }

}
