package cj.task.sleact.repository;

import cj.task.sleact.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByNameAndWorkspaceUrl(String name, String workspaceUrl);

}
