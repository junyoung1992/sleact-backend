package cj.task.sleact.persistence.repository;

import cj.task.sleact.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
