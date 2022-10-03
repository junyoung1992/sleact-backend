package cj.task.sleact.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "workspace_member", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "workspace_id"})})
public class WorkspaceMember extends BaseDate {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_id", referencedColumnName = "id", nullable = false)
    Workspace workspace;

    LocalDateTime loggedInAt = LocalDateTime.now();

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public WorkspaceMember(Member member, Workspace workspace) {
        this.member = member;
        this.workspace = workspace;
    }
}
