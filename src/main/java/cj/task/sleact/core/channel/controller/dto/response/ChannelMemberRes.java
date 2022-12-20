package cj.task.sleact.core.channel.controller.dto.response;

import cj.task.sleact.core.channel.mapper.ChannelMapper;
import cj.task.sleact.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChannelMemberRes {

    final Long id;
    final String name;
    final String email;

    @Builder
    public ChannelMemberRes(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static List<ChannelMemberRes> fromEntity(List<User> members) {
        List<ChannelMemberRes> result = new ArrayList<>();
        members.forEach(member -> result.add(ChannelMapper.INSTANCE.fromEntity(member)));
        return result;
    }

}
