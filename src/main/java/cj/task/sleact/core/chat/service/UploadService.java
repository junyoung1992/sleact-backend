package cj.task.sleact.core.chat.service;

import cj.task.sleact.config.auth.dto.SessionUser;
import cj.task.sleact.core.channel.component.ChannelComponent;
import cj.task.sleact.core.chat.component.ChatComponent;
import cj.task.sleact.core.chat.component.DMComponent;
import cj.task.sleact.core.chat.controller.response.ChatInfoRes;
import cj.task.sleact.core.chat.controller.response.DMInfoRes;
import cj.task.sleact.core.chat.mapper.ChatMapper;
import cj.task.sleact.core.chat.mapper.DmMapper;
import cj.task.sleact.core.workspace.component.WorkspaceComponent;
import cj.task.sleact.entity.Channel;
import cj.task.sleact.entity.ChannelChat;
import cj.task.sleact.entity.Dm;
import cj.task.sleact.entity.User;
import cj.task.sleact.entity.Workspace;
import cj.task.sleact.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
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
    public List<ChatInfoRes> uploadImages(String workspaceUrl, String channelName, Long memberId, List<MultipartFile> images) {
        Channel channel = findChannel(workspaceUrl, channelName);

        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("???????????? ?????? ??????????????????."));

        return images.stream()
                .map(image -> {
                    String path = saveFile(image);
                    String filePath = uploadDirectory + "/" + path;
                    ChannelChat channelChat = chatComponent.post(channel, user, filePath);
                    return ChatMapper.INSTANCE.fromEntity(channelChat);
                })
                .toList();
    }

    @Transactional
    public List<DMInfoRes> uploadImages(String workspaceUrl, Long receiverId, SessionUser user, List<MultipartFile> images) {
        Workspace workspace = workspaceComponent.findWorkspaceByUrl(workspaceUrl);

        User sender = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("???????????? ?????? ??????????????????."));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("???????????? ?????? ??????????????????."));

        for (MultipartFile image : images) {
            String path = saveFile(image);
            String filePath = uploadDirectory + "/" + path;
            dmComponent.post(workspace, sender, receiver, filePath);
        }

        return images.stream()
                .map(image -> {
                    String path = saveFile(image);
                    String filePath = uploadDirectory + "/" + path;
                    Dm dm = dmComponent.post(workspace, sender, receiver, filePath);
                    return DmMapper.INSTANCE.fromEntity(dm);
                })
                .toList();
    }

    private Channel findChannel(String workspaceUrl, String channelName) {
        workspaceComponent.findWorkspaceByUrl(workspaceUrl);
        return channelComponent.findByWorkspaceUrlAndChannelName(workspaceUrl, channelName);
    }

    private String saveFile(MultipartFile saveFile) {
        String fileName = getUploadFilePath(saveFile);

        Path path = Paths.get(uploadDirectory)
                .resolve(Paths.get(Objects.requireNonNull(fileName, "?????? ????????? ???????????? ????????????.")))
                .normalize()
                .toAbsolutePath();

        if (!path.getParent().equals(Paths.get(uploadDirectory).toAbsolutePath())) {
            // This is a security check
            throw new RuntimeException("?????? ????????? ???????????? ????????????.");
        }

        try (InputStream inputStream = saveFile.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            log.error("?????? ????????? ??????????????????.", e);
            throw new NullPointerException("?????? ????????? ??????????????????.");
        }
    }

    private String getUploadFilePath(MultipartFile saveFile) {
        return StringUtils.stripFilenameExtension(Objects.requireNonNull(saveFile.getOriginalFilename())) +
                "_" +
                Instant.now().toEpochMilli() +
                "." +
                StringUtils.getFilenameExtension(saveFile.getOriginalFilename());
    }

}
