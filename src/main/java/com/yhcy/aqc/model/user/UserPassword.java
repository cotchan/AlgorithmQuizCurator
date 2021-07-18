package com.yhcy.aqc.model.user;

import com.yhcy.aqc.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_pw")
public class UserPassword extends BaseTimeEntity {

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
}
