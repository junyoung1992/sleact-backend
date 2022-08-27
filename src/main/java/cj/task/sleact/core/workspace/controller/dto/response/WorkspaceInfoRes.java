package cj.task.sleact.core.workspace.controller.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkspaceInfoRes {

    private final Long id;
    private final String name;
    private final String url;
    private final Long ownerId;

    @Builder
    public WorkspaceInfoRes(Long id, String name, String url, Long ownerId) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.ownerId = ownerId;
    }

}
