package cj.task.sleact.core.workspace.controller;

import cj.task.sleact.common.constants.ApiUrlConstants;
import cj.task.sleact.config.auth.LoginUser;
import cj.task.sleact.config.auth.dto.SessionUser;
import cj.task.sleact.core.workspace.controller.dto.request.CreateWorkspaceReq;
import cj.task.sleact.core.workspace.controller.dto.response.WorkspaceInfoRes;
import cj.task.sleact.core.workspace.controller.dto.response.WorkspaceMemberRes;
import cj.task.sleact.core.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<WorkspaceInfoRes> getWorkspacesByUserId(@LoginUser SessionUser user) {
        return workspaceService.findWorkspacesBy(user);
    }

    @PostMapping
    public WorkspaceInfoRes createWorkspace(@LoginUser SessionUser user, @RequestBody @Valid CreateWorkspaceReq body) {
        return workspaceService.createWorkspaceWith(body, user.getId());
    }

    @GetMapping(value = ApiUrlConstants.Workspace.WORKSPACE_MEMBERS)
    public List<WorkspaceMemberRes> getMembersInWorkspace(@PathVariable(value = "workspace") String workspaceUrl) {
        return workspaceService.findMembersInWorkspace(workspaceUrl);
    }

    @GetMapping(value = ApiUrlConstants.Workspace.WORKSPACE_A_MEMBER)
    public WorkspaceMemberRes getMemberInfo(@PathVariable(value = "workspace") String workspaceUrl,
                                            @PathVariable(value = "member") Long memberId) {
        return workspaceService.findMemberInfo(workspaceUrl, memberId);
    }

}
