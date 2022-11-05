package cj.task.sleact.repository;

import cj.task.sleact.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);

    @Query("""
            select u
            from User u
            inner join u.workspaces wm
            where wm.workspace.id = :workspaceId
            and u.email = :email
            """)
    Optional<User> findOneByEmailAndWorkspaceId(@Param("email") String email, @Param("workspaceId") Long workspaceId);

    @Query("""
            select u
            from User u
            inner join u.workspaces wm
            where wm.workspace.id = :workspaceId
            and u.id = :userId
            """)
    Optional<User> findOneByUserIdAndWorkspaceId(@Param("userId") Long userId, @Param("workspaceId") Long workspaceId);

    @Query("""
            select u
            from WorkspaceMember wm
            inner join wm.user u
            where wm.workspace.id = :workspaceId
            """)
    List<User> findAllInWorkspace(@Param("workspaceId") Long workspaceId);

    @Query("""
            select u
            from ChannelMember cm
            inner join cm.user u
            where cm.channel.id = :channelId
            """)
    List<User> findAllInChannel(@Param("channelId") Long channelId);

}
