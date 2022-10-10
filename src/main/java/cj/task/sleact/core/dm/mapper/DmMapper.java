package cj.task.sleact.core.dm.mapper;

import cj.task.sleact.core.dm.controller.response.DMInfoRes;
import cj.task.sleact.entity.Dm;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface DmMapper {

    DmMapper INSTANCE = Mappers.getMapper(DmMapper.class);

    DMInfoRes fromEntity(Dm dm);

}
