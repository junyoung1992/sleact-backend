package cj.task.sleact.core.channel.mapper;

import cj.task.sleact.core.channel.controller.dto.response.ChannelInfoRes;
import cj.task.sleact.core.channel.controller.dto.response.ChannelMemberRes;
import cj.task.sleact.entity.Channel;
import cj.task.sleact.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ChannelMapper {

    ChannelMapper INSTANCE = Mappers.getMapper(ChannelMapper.class);

    @Mapping(target = "workspaceId", expression = "java( channel.getWorkspace().getId() )")
    ChannelInfoRes fromEntity(Channel channel);

    ChannelMemberRes fromEntity(User user);

}
