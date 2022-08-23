package cj.task.sleact.core.workspace.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateWorkspaceHttpReq {

    @NotBlank
    final String workspace;
    @NotBlank
    final String url;

    public CreateWorkspaceHttpReq(String workspace, String url) {
        this.workspace = workspace;
        this.url = url;
    }

}
