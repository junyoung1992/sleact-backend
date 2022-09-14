package cj.task.sleact.core.workspace.controller;

import cj.task.sleact.core.workspace.controller.dto.response.ChatInfoRes;
import cj.task.sleact.core.workspace.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cj.task.sleact.common.constants.ApiUrlConstants.Workspace.BASE_URL;
import static cj.task.sleact.common.constants.ApiUrlConstants.Workspace.CHAT;

@RestController
@RequestMapping(value = BASE_URL)
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping(value = CHAT)
    public List<ChatInfoRes> getChatList(@PathVariable(value = "workspace") String workspaceUrl,
                                         @PathVariable(value = "channel") String channelName,
                                         @RequestParam(value = "perPage") Long perPage,
                                         @RequestParam(value = "page") Long page) {
        return chatService.findPagingList(workspaceUrl, channelName, perPage, page);
    }

}
