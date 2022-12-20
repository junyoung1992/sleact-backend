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
public class CreateWorkspaceReq {

    @NotBlank
    String workspace;
    @NotBlank
    String url;

    @Builder
    public CreateWorkspaceReq(String workspace, String url) {
        this.workspace = workspace;
        this.url = url;
    }

}
