package cj.task.sleact.core.chat.controller.response;

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
    final String username;
    final String email;
    //    final User user;
    final String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    final LocalDateTime createdAt;
    final Long channelId;
    final String channelName;
//    final Channel channel;

    @Builder
    public ChatInfoRes(Long id, Long userId, /*User user,*/ String username, String email, String content, LocalDateTime createdAt, Long channelId,/* Channel channel,*/ String channelName) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.email = email;
//        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
        this.channelId = channelId;
//        this.channel = channel;
        this.channelName = channelName;
    }

}
