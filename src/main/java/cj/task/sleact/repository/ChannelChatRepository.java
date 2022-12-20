package cj.task.sleact.repository;

import cj.task.sleact.entity.ChannelChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChannelChatRepository extends JpaRepository<ChannelChat, Long> {

    @Query(value = """
            select c
            from ChannelChat c
            inner join fetch c.user m
            where c.channel.id = :channelId
            """)
    List<ChannelChat> findPagingAndFetchMemberByChannelId(@Param("channelId") Long channelId);

    Long countChannelChatByChannelIdAndCreatedAtAfter(Long channelId, LocalDateTime createdAt);

}
