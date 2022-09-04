package cj.task.sleact.core.workspace.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ChannelInfoRes {

    private final Long id;
    private final String name;
    @JsonProperty(value = "private")
    private final boolean privates;
    private final Long workspaceId;

    @Builder
    public ChannelInfoRes(Long id, String name, boolean privates, Long workspaceId) {
        this.id = id;
        this.name = name;
        this.privates = privates;
        this.workspaceId = workspaceId;
    }

}