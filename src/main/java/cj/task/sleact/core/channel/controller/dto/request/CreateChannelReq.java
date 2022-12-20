package cj.task.sleact.core.channel.controller.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CreateChannelReq {

    @NotBlank
    String name;

    @Builder
    public CreateChannelReq(String name) {
        this.name = name;
    }

}
