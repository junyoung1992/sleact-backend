package cj.task.sleact.core.dm.service;

import cj.task.sleact.config.auth.dto.SessionUser;
import cj.task.sleact.core.dm.component.DMComponent;
import cj.task.sleact.core.dm.controller.request.PostDmReq;
import cj.task.sleact.core.dm.controller.response.DMInfoRes;
import cj.task.sleact.core.dm.mapper.DmMapper;
import cj.task.sleact.core.workspace.component.WorkspaceComponent;
import cj.task.sleact.entity.BaseDate;
import cj.task.sleact.entity.User;
import cj.task.sleact.entity.Workspace;
import cj.task.sleact.repository.DMRepository;
import cj.task.sleact.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DMService {

    private final WorkspaceComponent workspaceComponent;
    private final DMComponent dmComponent;
    private final DMRepository dmRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<DMInfoRes> findDms(String workspaceUrl, SessionUser user, Long theOtherId, Long perPage, Long page) {
        Workspace workspace = workspaceComponent.findWorkspaceByUrl(workspaceUrl);
        return dmRepository.findAllByWorkspaceIdAndReceiverIdAndSenderId(workspace.getId(), user.getId(), theOtherId).stream()
                .sorted(Comparator.comparing(BaseDate::getCreatedAt).reversed())
                .skip(perPage * (page - 1))
                .limit(perPage)
                .map(DmMapper.INSTANCE::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public Long countUnreadChat(String workspaceUrl, SessionUser user, Long theOtherId, LocalDateTime target) {
        Workspace workspace = workspaceComponent.findWorkspaceByUrl(workspaceUrl);
        return dmRepository.countDmsByWorkspaceIdAndReceiverIdAndSenderIdAndCreatedAtAfter(workspace.getId(), user.getId(), theOtherId, target);
    }

    @Transactional
    public void post(String workspaceUrl, Long receiverId, SessionUser user, PostDmReq request) {
        Workspace workspace = workspaceComponent.findWorkspaceByUrl(workspaceUrl);

        User sender = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        dmComponent.post(workspace, sender, receiver, request.getContent());
    }
}
