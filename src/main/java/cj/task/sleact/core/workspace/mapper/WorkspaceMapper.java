package cj.task.sleact.core.workspace.mapper;

import cj.task.sleact.core.workspace.dto.request.CreateWorkspaceHttpReq;
import cj.task.sleact.core.workspace.dto.request.CreateWorkspaceReq;
import cj.task.sleact.core.workspace.dto.response.WorkspaceInfoRes;
import cj.task.sleact.persistence.entity.Workspace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface WorkspaceMapper {

    WorkspaceMapper INSTANCE = Mappers.getMapper(WorkspaceMapper.class);

    CreateWorkspaceReq fromHttpReq(CreateWorkspaceHttpReq req, Long userId);

    @Mapping(expression = "java( workspace.getOwner().getId() )", target = "ownerId")
    WorkspaceInfoRes fromEntity(Workspace workspace);

}
