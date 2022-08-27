package cj.task.sleact.repository;

import cj.task.sleact.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    @Query(value = """
        select w
        from Workspace w
        inner join w.members m
        where m.id = :memberId
        """)
    List<Workspace> findAllByMemberId(@Param("memberId") Long memberId);

    Optional<Workspace> findOneByUrl(String url);

}
