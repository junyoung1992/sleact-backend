package cj.task.sleact.core.chat.controller;

import cj.task.sleact.config.auth.LoginUser;
import cj.task.sleact.config.auth.dto.SessionUser;
import cj.task.sleact.core.chat.controller.request.PostChatReq;
import cj.task.sleact.core.chat.controller.response.ChatInfoRes;
import cj.task.sleact.core.chat.service.ChatService;
import cj.task.sleact.core.chat.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate template;

    @GetMapping(value = CHAT)
    public List<ChatInfoRes> getChatList(@PathVariable(value = "workspace") String workspaceUrl,
                                         @PathVariable(value = "channel") String channelName,
                                         @RequestParam(value = "perPage") Long perPage,
                                         @RequestParam(value = "page") Long page) {
        return chatService.findPagingList(workspaceUrl, channelName, perPage, page);
    }

    @PostMapping(value = CHAT)
    public void chat(@LoginUser SessionUser user,
                     @PathVariable(value = "workspace") String workspaceUrl,
                     @PathVariable(value = "channel") String channelName,
                     @RequestBody @Valid PostChatReq body) {
        ChatInfoRes post = chatService.post(workspaceUrl, channelName, user.getId(), body);
        template.convertAndSend("/topic/ws-" + workspaceUrl + "-" + channelName, post);
    }

    @PostMapping(value = IMAGE)
    public void uploadImages(@LoginUser SessionUser user,
                             @PathVariable(value = "workspace") String workspaceUrl,
                             @PathVariable(value = "channel") String channelName,
                             @RequestPart(value = "image") List<MultipartFile> images) {
        List<ChatInfoRes> uploads = uploadService.uploadImages(workspaceUrl, channelName, user.getId(), images);
        uploads.forEach(upload -> template.convertAndSend("/topic/messages", upload));
    }

    @GetMapping(value = UNREAD)
    public Long getUnreadCount(@PathVariable(value = "workspace") String workspaceUrl,
                               @PathVariable(value = "channel") String channelName,
                               @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @RequestParam(value = "after") LocalDateTime after) {
        return chatService.countUnreadChat(workspaceUrl, channelName, after);
    }

}
