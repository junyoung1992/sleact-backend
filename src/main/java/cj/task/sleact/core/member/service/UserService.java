package cj.task.sleact.core.member.service;

import cj.task.sleact.core.member.controller.dto.response.UserInfoRes;
import cj.task.sleact.core.member.mapper.UserMapper;
import cj.task.sleact.entity.User;
import cj.task.sleact.entity.Workspace;
import cj.task.sleact.repository.UserRepository;
import cj.task.sleact.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;

    @Transactional(readOnly = true)
    public UserInfoRes findUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
        List<Workspace> workspaces = workspaceRepository.findAllByMemberId(userId);
        return UserMapper.INSTANCE.fromEntity(user, workspaces);
    }

}
