package cj.task.sleact.core.workspace.controller;

import cj.task.sleact.common.constants.ApiUrlConstants;
import cj.task.sleact.config.auth.LoginUser;
import cj.task.sleact.config.auth.dto.SessionUser;
import cj.task.sleact.core.workspace.controller.dto.request.CreateChannelReq;
import cj.task.sleact.core.workspace.controller.dto.response.ChannelInfoRes;
import cj.task.sleact.core.workspace.service.ChannelService;
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
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping(value = ApiUrlConstants.Workspace.CHANNELS)
    public List<ChannelInfoRes> getChannelsByWorkspaceAndUserId(@LoginUser SessionUser user,
                                                                @PathVariable(value = "workspace") String workspaceUrl) {
        return channelService.findChannelsBy(workspaceUrl, user.getId());
    }

    @PostMapping(value = ApiUrlConstants.Workspace.CHANNELS)
    public ChannelInfoRes createChannel(@LoginUser SessionUser user,
                                        @PathVariable(value = "workspace") String workspaceUrl,
                                        @RequestBody @Valid CreateChannelReq body) {
        return channelService.createChannelWith(body, workspaceUrl, user.getId());
    }

    @GetMapping(value = ApiUrlConstants.Workspace.A_CHANNEL)
    public ChannelInfoRes getChannelInfo(@PathVariable(value = "workspace") String workspaceUrl,
                                         @PathVariable(value = "channel") String channelName) {
        return channelService.findChannelInfoBy(workspaceUrl, channelName);
    }

}
