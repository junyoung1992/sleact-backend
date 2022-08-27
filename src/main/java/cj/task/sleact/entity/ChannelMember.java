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

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "channel_member", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "channel_id"})})
public class ChannelMember extends BaseDate {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_id", referencedColumnName = "id")
    Channel channel;

    @Builder(builderClassName = "createBuilder", builderMethodName = "createBuilder")
    public ChannelMember(User user, Channel channel) {
        this.user = user;
        this.channel = channel;
    }

}
