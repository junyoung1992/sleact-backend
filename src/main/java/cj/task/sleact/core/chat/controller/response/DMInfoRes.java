package cj.task.sleact.core.chat.controller.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DMInfoRes {

    final Long id;
    final String content;
    final Long senderId;
    final String senderName;
    final String senderEmail;
    final Long receiverId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    final LocalDateTime createdAt;

    @Builder
    public DMInfoRes(Long id, String content, Long senderId, String senderName, String senderEmail, Long receiverId, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.receiverId = receiverId;
        this.createdAt = createdAt;
    }

}
