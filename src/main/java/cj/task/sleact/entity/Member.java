package cj.task.sleact.entity;

import cj.task.sleact.common.enums.MemberRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class Member extends BaseDate {

    @Id
    @GeneratedValue
    Long id;

    @NotBlank
    @Length(max = 30)
    @Column(nullable = false, unique = true, length = 30)
    String email;

    @NotBlank
    @Length(max = 30)
    @Column(nullable = false, length = 30)
    String nickname;

    @NotBlank
    @Length(max = 100)
    @Column(nullable = false, length = 100)
    String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    MemberRole role;

    LocalDateTime deletedAt;

    @OneToMany(mappedBy = "owner")
    List<Workspace> owned = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    List<WorkspaceMember> workspaces = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    List<ChannelMember> channels = new ArrayList<>();

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public Member(String email, String nickname, String password, MemberRole role) {
        Assert.hasText(email, "email must be not blank");
        Assert.hasText(nickname, "nickname must be not blank");
        Assert.hasText(password, "password must be not blank");

        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }
}
