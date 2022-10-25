package cj.task.sleact.core.chat.mapper;

import cj.task.sleact.core.chat.controller.response.ChatInfoRes;
import cj.task.sleact.entity.ChannelChat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ChatMapper {

    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    @Mapping(target = "userId", expression = "java( channelChat.getUser().getId() )")
    @Mapping(target = "username", expression = "java( channelChat.getUser().getName() )")
    @Mapping(target = "email", expression = "java( channelChat.getUser().getEmail() )")
    @Mapping(target = "channelId", expression = "java( channelChat.getChannel().getId() )")
    @Mapping(target = "channelName", expression = "java( channelChat.getChannel().getName() )")
    ChatInfoRes fromEntity(ChannelChat channelChat);

}
