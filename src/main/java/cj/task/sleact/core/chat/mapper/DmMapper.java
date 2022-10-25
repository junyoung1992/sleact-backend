package cj.task.sleact.core.chat.mapper;

import cj.task.sleact.core.chat.controller.response.DMInfoRes;
import cj.task.sleact.entity.Dm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface DmMapper {

    DmMapper INSTANCE = Mappers.getMapper(DmMapper.class);

    @Mapping(target = "senderId", expression = "java( dm.getSender().getId() )")
    @Mapping(target = "senderName", expression = "java( dm.getSender().getName() )")
    @Mapping(target = "senderEmail", expression = "java( dm.getSender().getEmail() )")
    @Mapping(target = "receiverId", expression = "java( dm.getReceiver().getId() )")
    DMInfoRes fromEntity(Dm dm);

}
