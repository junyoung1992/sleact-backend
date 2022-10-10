package cj.task.sleact.core.workspace.component;

import cj.task.sleact.core.workspace.controller.dto.response.ChatInfoRes;
import cj.task.sleact.core.workspace.mapper.ChatMapper;
import cj.task.sleact.entity.BaseDate;
import cj.task.sleact.entity.Channel;
import cj.task.sleact.entity.ChannelChat;
import cj.task.sleact.entity.User;
import cj.task.sleact.repository.ChannelChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatComponent {

    private final ChannelChatRepository channelChatRepository;

    public List<ChatInfoRes> findPagingList(Long channelId, Long perPage, Long page) {
        return channelChatRepository.findPagingAndFetchMemberByChannelId(channelId).stream()
                .sorted(Comparator.comparing(BaseDate::getCreatedAt).reversed())
                .skip(perPage * (page - 1))
                .limit(perPage)
                .map(ChatMapper.INSTANCE::fromEntity)
                .toList();
    }

    public Long countChannelChatAfterTarget(Long channelId, LocalDateTime target) {
        return channelChatRepository.countChannelChatByChannelIdAndCreatedAtAfter(channelId, target);
    }

    public void post(Channel channel, User user, String content) {
        ChannelChat upload = ChannelChat.createBuilder()
                .channel(channel)
                .user(user)
                .content(content)
                .build();
        channelChatRepository.save(upload);
    }

}
