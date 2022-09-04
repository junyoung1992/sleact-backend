package cj.task.sleact.core.member.mapper;

import cj.task.sleact.core.member.controller.dto.response.MemberInfoRes;
import cj.task.sleact.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberInfoRes fromEntity(Member member);

}
