package cj.task.sleact.core.chat.component;

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

    public List<ChannelChat> findPagingList(Long channelId, Long perPage, Long page) {
        return channelChatRepository.findPagingAndFetchMemberByChannelId(channelId).stream()
                .sorted(Comparator.comparing(BaseDate::getCreatedAt).reversed())
                .skip(perPage * (page - 1))
                .limit(perPage)
                .toList();
    }

    public Long countChannelChatAfterTarget(Long channelId, LocalDateTime target) {
        return channelChatRepository.countChannelChatByChannelIdAndCreatedAtAfter(channelId, target);
    }

    public ChannelChat post(Channel channel, User user, String content) {
        ChannelChat post = ChannelChat.createBuilder()
                .channel(channel)
                .user(user)
                .content(content)
                .build();
        channelChatRepository.save(post);
        return post;
    }

}
