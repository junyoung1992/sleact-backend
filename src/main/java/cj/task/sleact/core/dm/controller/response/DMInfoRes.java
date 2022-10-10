package cj.task.sleact.core.dm.controller.response;

import cj.task.sleact.entity.User;
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
    final UserInfoRes sender;
    final UserInfoRes receiver;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    final LocalDateTime createdAt;

    @Builder
    public DMInfoRes(Long id, String content, LocalDateTime createdAt, User sender, User receiver) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.sender = UserInfoRes.fromUser(sender);
        this.receiver = UserInfoRes.fromUser(receiver);
    }

    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private static class UserInfoRes {
        final Long id;
        final String name;
        final String email;

        @Builder
        public UserInfoRes(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public static UserInfoRes fromUser(User user) {
            return UserInfoRes.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
        }
    }

}
