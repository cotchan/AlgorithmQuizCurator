package com.yhcy.aqc.model.user;

import com.yhcy.aqc.model.CreateTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_pw")
public class UserPassword extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(nullable = false, name = "password")
    private String password;

    @Builder
    public UserPassword(Integer seq, User user, String password) {
        this.seq = seq;
        this.user = user;
        this.password = password;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("seq", seq)
            .append("user", user)
            .append("password", "UserPassword is secret value.")
            .toString();
    }
}
