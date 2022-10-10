package cj.task.sleact.core.workspace.mapper;

import cj.task.sleact.core.workspace.controller.dto.response.WorkspaceInfoRes;
import cj.task.sleact.core.workspace.controller.dto.response.WorkspaceMemberRes;
import cj.task.sleact.entity.User;
import cj.task.sleact.entity.Workspace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface WorkspaceMapper {

    WorkspaceMapper INSTANCE = Mappers.getMapper(WorkspaceMapper.class);

    @Mapping(expression = "java( workspace.getOwner().getId() )", target = "ownerId")
    WorkspaceInfoRes fromEntity(Workspace workspace);

    WorkspaceMemberRes fromEntity(User user);

}
