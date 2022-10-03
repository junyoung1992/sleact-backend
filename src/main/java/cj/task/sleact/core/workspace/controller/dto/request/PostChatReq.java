package cj.task.sleact.core.workspace.controller.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PostChatReq {

    @NotBlank
    String content;

    @Builder
    public PostChatReq(String content) {
        this.content = content;
    }
    
}
