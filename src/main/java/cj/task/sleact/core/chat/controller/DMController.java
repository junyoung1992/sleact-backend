package cj.task.sleact.core.chat.controller;

import cj.task.sleact.common.constants.ApiUrlConstants;
import cj.task.sleact.config.auth.LoginUser;
import cj.task.sleact.config.auth.dto.SessionUser;
import cj.task.sleact.core.chat.controller.request.PostDmReq;
import cj.task.sleact.core.chat.controller.response.DMInfoRes;
import cj.task.sleact.core.chat.service.DMService;
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

@RestController
@RequestMapping(value = ApiUrlConstants.DM.BASE_URL)
@RequiredArgsConstructor
public class DMController {

    private final DMService dmService;
    private final UploadService uploadService;
    private final SimpMessagingTemplate template;

    @GetMapping(value = ApiUrlConstants.DM.CHAT)
    public List<DMInfoRes> getDMList(@LoginUser SessionUser user,
                                     @PathVariable(value = "workspace") String workspaceUrl,
                                     @PathVariable(value = "user") Long theOtherId,
                                     @RequestParam(value = "perPage") Long perPage,
                                     @RequestParam(value = "page") Long page) {
        return dmService.findDms(workspaceUrl, user, theOtherId, perPage, page);
    }

    @PostMapping(value = ApiUrlConstants.DM.CHAT)
    public void dm(@LoginUser SessionUser user,
                   @PathVariable(value = "workspace") String workspaceUrl,
                   @PathVariable(value = "user") Long theOtherId,
                   @RequestBody @Valid PostDmReq body) {
        DMInfoRes post = dmService.post(workspaceUrl, theOtherId, user, body);
        template.convertAndSend("/topic/ws-" + workspaceUrl, post);
    }

    @PostMapping(value = ApiUrlConstants.DM.IMAGE)
    public void uploadImages(@LoginUser SessionUser user,
                             @PathVariable(value = "workspace") String workspaceUrl,
                             @PathVariable(value = "user") Long theOtherId,
                             @RequestPart(value = "image") List<MultipartFile> images) {
        List<DMInfoRes> post = uploadService.uploadImages(workspaceUrl, theOtherId, user, images);
        template.convertAndSend("/topic/ws-" + workspaceUrl, post);
    }


    @GetMapping(value = ApiUrlConstants.DM.UNREAD)
    public Long getUnreadCount(@LoginUser SessionUser user,
                               @PathVariable(value = "workspace") String workspaceUrl,
                               @PathVariable(value = "user") Long theOtherId,
                               @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @RequestParam(value = "after") LocalDateTime after) {
        return dmService.countUnreadChat(workspaceUrl, user, theOtherId, after);
    }

}
