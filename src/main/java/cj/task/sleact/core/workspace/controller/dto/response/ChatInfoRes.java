package cj.task.sleact.core.workspace.controller.dto.response;

import cj.task.sleact.entity.Channel;
import cj.task.sleact.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatInfoRes {

    Long id;
    Long userId;
    Member user;
    String content;
    LocalDateTime createdAt;
    Long channelId;
    Channel channel;

    @Builder
    public ChatInfoRes(Long id, Long userId, Member user, String content, LocalDateTime createdAt, Long channelId, Channel channel) {
        this.id = id;
        this.userId = userId;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
        this.channelId = channelId;
        this.channel = channel;
    }
}
