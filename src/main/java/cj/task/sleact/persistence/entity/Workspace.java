package cj.task.sleact.persistence.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "workspace")
public class Workspace extends BaseDate {

    @Id
    @GeneratedValue
    Long id;

    @NotBlank
    @Length(max = 30)
    @Column(nullable = false, unique = true, length = 30)
    String name;

    @NotBlank
    @Length(max = 30)
    @Column(nullable = false, unique = true, length = 30)
    String url;

    LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    User owner;

    @OneToMany(mappedBy = "workspace")
    List<WorkspaceMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "workspace")
    List<Channel> channels = new ArrayList<>();

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public Workspace(String name, String url, User owner) {
        Assert.hasText(name, "Workspace name must be not blank");
        Assert.hasText(url, "Workspace url must be not blank");
        Assert.notNull(owner, "Workspace owner must be not null");

        this.name = name;
        this.url = url;
        setOwner(owner);
    }

    private void setOwner(User owner) {
        this.owner = owner;
        owner.getOwned().add(this);
    }

}
