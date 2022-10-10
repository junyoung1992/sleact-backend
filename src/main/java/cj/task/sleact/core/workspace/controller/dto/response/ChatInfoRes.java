package cj.task.sleact.core.workspace.controller.dto.response;

import cj.task.sleact.entity.Channel;
import cj.task.sleact.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatInfoRes {

    final Long id;
    final Long userId;
    final User user;
    final String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    final LocalDateTime createdAt;
    final Long channelId;
    final Channel channel;

    @Builder
    public ChatInfoRes(Long id, Long userId, User user, String content, LocalDateTime createdAt, Long channelId, Channel channel) {
        this.id = id;
        this.userId = userId;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
        this.channelId = channelId;
        this.channel = channel;
    }
}
