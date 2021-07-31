package com.yhcy.aqc.model.user;

import com.yhcy.aqc.model.CreateUpdateTimeEntity;
import com.yhcy.aqc.security.Jwt;
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
@Table(name = "user")
public class User extends CreateUpdateTimeEntity {

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("seq", seq)
            .append("userId", userId)
            .append("nickname", nickname)
            .append("verifyQuestion", verifyQuestion)
            .append("verifyAnswer", verifyAnswer)
            .append("role", role)
            .toString();
    }
}
