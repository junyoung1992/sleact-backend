package cj.task.sleact.repository;

import cj.task.sleact.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findOneByEmail(String email);

}
