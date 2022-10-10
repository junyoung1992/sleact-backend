package cj.task.sleact.core.dm.controller.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PostDmReq {

    @NotBlank
    String content;

    @Builder
    public PostDmReq(String content) {
        this.content = content;
    }
    
}
