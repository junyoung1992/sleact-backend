package cj.task.sleact.persistence.repository;

import cj.task.sleact.persistence.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
