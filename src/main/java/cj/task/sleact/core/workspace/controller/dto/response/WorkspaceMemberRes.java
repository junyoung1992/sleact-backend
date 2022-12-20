package cj.task.sleact.core.workspace.controller.dto.response;

import cj.task.sleact.core.workspace.mapper.WorkspaceMapper;
import cj.task.sleact.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkspaceMemberRes {

    final Long id;
    final String name;
    final String email;

    @Builder
    public WorkspaceMemberRes(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static List<WorkspaceMemberRes> fromEntity(List<User> members) {
        List<WorkspaceMemberRes> result = new ArrayList<>();
        members.forEach(member -> result.add(WorkspaceMapper.INSTANCE.fromEntity(member)));
        return result;
    }

}
