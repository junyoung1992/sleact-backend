package cj.task.sleact.entity;

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
import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "channel")
public class Channel extends BaseDate {

    @Id
    @GeneratedValue
    Long id;

    @NotBlank
    @Length(max = 30)
    @Column(nullable = false, length = 30)
    String name;

    @Column(name = "private")
    Boolean privates = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", referencedColumnName = "id")
    Workspace workspace;

    @OneToMany(mappedBy = "channel")
    List<ChannelMember> members = new ArrayList<>();

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public Channel(String name, Workspace workspace) {
        Assert.hasText(name, "Channel name must be not blank");
        Assert.notNull(workspace, "Channel workspace must be not null");

        this.name = name;
        this.workspace = workspace;
    }

}
