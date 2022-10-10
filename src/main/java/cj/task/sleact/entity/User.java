package cj.task.sleact.entity;

import cj.task.sleact.common.enums.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseDate {

    @Id
    @GeneratedValue
    Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    String email;

    @NotBlank
    @Column(nullable = false)
    String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserRole role;

    @OneToMany(mappedBy = "owner")
    List<Workspace> owned = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<WorkspaceMember> workspaces = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    List<ChannelMember> channels = new ArrayList<>();

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public User(String email, String name, UserRole role) {
        Assert.hasText(email, "email must be not blank");
        Assert.hasText(name, "nickname must be not blank");
        Assert.notNull(role, "role must be not null");

        this.email = email;
        this.name = name;
        this.role = role;
    }

    public User updateName(String name) {
        this.name = name;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
