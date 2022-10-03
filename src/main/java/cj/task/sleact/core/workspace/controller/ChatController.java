package cj.task.sleact.core.workspace.controller;

import cj.task.sleact.core.workspace.controller.dto.request.PostChatReq;
import cj.task.sleact.core.workspace.controller.dto.response.ChatInfoRes;
import cj.task.sleact.core.workspace.service.ChatService;
import cj.task.sleact.core.workspace.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static cj.task.sleact.common.constants.ApiUrlConstants.Workspace.BASE_URL;
import static cj.task.sleact.common.constants.ApiUrlConstants.Workspace.CHAT;
import static cj.task.sleact.common.constants.ApiUrlConstants.Workspace.IMAGE;
import static cj.task.sleact.common.constants.ApiUrlConstants.Workspace.UNREAD;

@RestController
@RequestMapping(value = BASE_URL)
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UploadService uploadService;

    @GetMapping(value = CHAT)
    public List<ChatInfoRes> getChatList(@PathVariable(value = "workspace") String workspaceUrl,
                                         @PathVariable(value = "channel") String channelName,
                                         @RequestParam(value = "perPage") Long perPage,
                                         @RequestParam(value = "page") Long page) {
        return chatService.findPagingList(workspaceUrl, channelName, perPage, page);
    }

    @PostMapping(value = CHAT)
    public void chat(@PathVariable(value = "workspace") String workspaceUrl,
                     @PathVariable(value = "channel") String channelName,
                     @RequestBody @Valid PostChatReq body) {
        chatService.post(workspaceUrl, channelName, 1L, body);
    }

    @PostMapping(value = IMAGE)
    public void uploadImages(@PathVariable(value = "workspace") String workspaceUrl,
                             @PathVariable(value = "channel") String channelName,
                             @RequestPart(value = "image") List<MultipartFile> images) {
        uploadService.uploadImages(workspaceUrl, channelName, 1L, images);
    }

    @GetMapping(value = UNREAD)
    public Long getUnreadCount(@PathVariable(value = "workspace") String workspaceUrl,
                               @PathVariable(value = "channel") String channelName,
                               @RequestParam(value = "after") LocalDateTime after) {
        return chatService.countUnreadChat(workspaceUrl, channelName, after);
    }

}
