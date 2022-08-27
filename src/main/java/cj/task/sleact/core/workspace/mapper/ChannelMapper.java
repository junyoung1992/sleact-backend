package cj.task.sleact.core.workspace.mapper;

import cj.task.sleact.core.workspace.controller.dto.response.ChannelInfoRes;
import cj.task.sleact.entity.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ChannelMapper {

    ChannelMapper INSTANCE = Mappers.getMapper(ChannelMapper.class);

    @Mapping(expression = "java( channel.getWorkspace().getId() )", target = "workspaceId")
    ChannelInfoRes fromEntity(Channel channel);

}
