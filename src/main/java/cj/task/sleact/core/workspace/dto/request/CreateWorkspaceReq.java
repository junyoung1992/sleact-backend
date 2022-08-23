package cj.task.sleact.core.workspace.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateWorkspaceReq {
    final String workspace;
    final String url;
    final Long userId;

    public CreateWorkspaceReq(String workspace, String url, Long userId) {
        this.workspace = workspace;
        this.url = url;
        this.userId = userId;
    }

}
