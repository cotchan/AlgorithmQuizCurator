package com.yhcy.aqc.model.user;

import com.yhcy.aqc.model.BaseTimeEntity;
import com.yhcy.aqc.security.Jwt;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(nullable = false, name = "id")
    private String userId;

    @Column(nullable = false, name = "nickname")
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "verify_question_seq")
    private VerifyQuestion verifyQuestion;

    @Column(nullable = true, name = "verify_question_answer")
    private String verifyAnswer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role_code")
    private Role role;

    public String newApiToken(Jwt jwt, String[] roles) {
        Jwt.Claims claims = Jwt.Claims.of(seq, userId, nickname, roles);
        return jwt.newToken(claims);
    }

    @Builder
    public User(Integer seq, String userId, String nickname, VerifyQuestion verifyQuestion, String verifyAnswer, Role role) {
        this.seq = seq;
        this.userId = userId;
        this.nickname = nickname;
        this.verifyQuestion = verifyQuestion;
        this.verifyAnswer = verifyAnswer;
        this.role = role;
    }

    public void update(VerifyQuestion verifyQuestion, String verifyAnswer) {
        this.verifyQuestion = verifyQuestion;
        this.verifyAnswer = verifyAnswer;
    }
}
