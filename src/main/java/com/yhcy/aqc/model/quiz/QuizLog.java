package com.yhcy.aqc.model.quiz;

import com.yhcy.aqc.model.CreateTimeEntity;
import com.yhcy.aqc.model.user.User;
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
@Table(name = "quiz_log")
public class QuizLog extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_seq")
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_seq")
    private QuizStateType quizStateType;

    @Builder
    public QuizLog(Integer seq, User user, Quiz quiz, QuizStateType quizStateType) {
        this.seq = seq;
        this.user = user;
        this.quiz = quiz;
        this.quizStateType = quizStateType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("seq", seq)
            .append("user", user)
            .append("quiz", quiz)
            .append("quizStateType", quizStateType)
            .toString();
    }
}
