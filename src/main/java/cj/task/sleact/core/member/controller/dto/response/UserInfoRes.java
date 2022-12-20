package cj.task.sleact.core.member.controller.dto.response;

import cj.task.sleact.entity.Workspace;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoRes {

    final Long id;
    final String name;
    final String email;
    final List<WorkspaceInfoRes> workspaces;

    @Builder
    public UserInfoRes(Long id, String name, String email, List<Workspace> workspaces) {
        this.id = id;
        this.name = name;
        this.email = email;

        this.workspaces = workspaces.stream()
                .map(w -> WorkspaceInfoRes.builder()
                        .id(w.getId())
                        .name(w.getName())
                        .url(w.getUrl())
                        .ownerId(w.getOwner().getId())
                        .build())
                .collect(Collectors.toList());
    }

    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class WorkspaceInfoRes {

        final Long id;
        final String name;
        final String url;
        final Long ownerId;

        @Builder
        public WorkspaceInfoRes(Long id, String name, String url, Long ownerId) {
            this.id = id;
            this.name = name;
            this.url = url;
            this.ownerId = ownerId;
        }

    }

}
