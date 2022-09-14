package cj.task.sleact.repository;

import cj.task.sleact.entity.ChannelChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChannelChatRepository extends JpaRepository<ChannelChat, Long> {

    @Query(value = """
            select c
            from ChannelChat c
            inner join fetch c.member m
            where c.channel.id = :channelId
            """)
    List<ChannelChat> findPagingAndFetchMemberByChannelId(Long channelId);

}
