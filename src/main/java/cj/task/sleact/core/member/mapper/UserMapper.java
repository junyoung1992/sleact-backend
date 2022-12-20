package cj.task.sleact.core.member.mapper;

import cj.task.sleact.common.enums.UserRole;
import cj.task.sleact.config.auth.dto.OAuthAttributes;
import cj.task.sleact.core.member.controller.dto.response.UserInfoRes;
import cj.task.sleact.entity.User;
import cj.task.sleact.entity.Workspace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface UserMapper {

    UserRole USER = UserRole.USER;

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "workspaces", source = "workspaces")
    UserInfoRes fromEntity(User user, List<Workspace> workspaces);

    @Mapping(target = "role", expression = "java( USER )")
    User fromOAuthToEntity(OAuthAttributes oAuthAttributes);

}
