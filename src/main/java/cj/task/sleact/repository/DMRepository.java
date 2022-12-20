package cj.task.sleact.repository;

import cj.task.sleact.entity.Dm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DMRepository extends JpaRepository<Dm, Long> {

    @Query("""
            select d
            from Dm d
            inner join fetch d.receiver r
            inner join fetch d.sender s
            where d.workspace.id = :workspaceId
            and d.receiver.id = :receiverId
            and d.sender.id = :senderId
            """)
    List<Dm> findAllByWorkspaceIdAndReceiverIdAndSenderId(@Param("workspaceId") Long workspaceId, @Param("receiverId") Long receiverId, @Param("senderId") Long senderId);

    Long countDmsByWorkspaceIdAndReceiverIdAndSenderIdAndCreatedAtAfter(Long workspaceId, Long receiverId, Long senderId, LocalDateTime createdAt);

}
