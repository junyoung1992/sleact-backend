package cj.task.sleact.core.workspace.service;

import cj.task.sleact.config.auth.dto.SessionUser;
import cj.task.sleact.core.dm.component.DMComponent;
import cj.task.sleact.core.workspace.component.ChannelComponent;
import cj.task.sleact.core.workspace.component.ChatComponent;
import cj.task.sleact.core.workspace.component.WorkspaceComponent;
import cj.task.sleact.entity.Channel;
import cj.task.sleact.entity.User;
import cj.task.sleact.entity.Workspace;
import cj.task.sleact.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadService {

    private final WorkspaceComponent workspaceComponent;
    private final ChannelComponent channelComponent;
    private final ChatComponent chatComponent;
    private final DMComponent dmComponent;
    private final UserRepository userRepository;

    @Value("${path.uploads}")
    private String uploadDirectory;

    @Transactional
    public void uploadImages(String workspaceUrl, String channelName, Long memberId, List<MultipartFile> images) {
        Channel channel = findChannel(workspaceUrl, channelName);

        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        for (MultipartFile image : images) {
            Path path = saveFile(image);
            chatComponent.post(channel, user, path.toString());
        }
    }

    @Transactional
    public void uploadImages(String workspaceUrl, Long receiverId, SessionUser user, List<MultipartFile> images) {
        Workspace workspace = workspaceComponent.findWorkspaceByUrl(workspaceUrl);

        User sender = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        for (MultipartFile image : images) {
            Path path = saveFile(image);
            dmComponent.post(workspace, sender, receiver, path.toString());
        }
    }

    private Path saveFile(MultipartFile image) {
        Path path = Paths.get(uploadDirectory)
                .resolve(Paths.get(Objects.requireNonNull(image.getOriginalFilename(), "파일 정보가 올바르지 않습니다.")))
                .normalize()
                .toAbsolutePath();

        if (!path.getParent().equals(Paths.get(uploadDirectory).toAbsolutePath())) {
            // This is a security check
            throw new RuntimeException("파일 경로가 올바르지 않습니다.");
        }

        try (InputStream inputStream = image.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            return path;
        } catch (IOException e) {
            log.error("파일 저장이 실패했습니다.", e);
            throw new NullPointerException("파일 저장이 실패했습니다.");
        }
    }

    private Channel findChannel(String workspaceUrl, String channelName) {
        workspaceComponent.findWorkspaceByUrl(workspaceUrl);
        return channelComponent.findByWorkspaceUrlAndChannelName(workspaceUrl, channelName);
    }

}
