package com.yhcy.aqc.model.quiz;

import com.yhcy.aqc.model.CreateUpdateTimeEntity;
import com.yhcy.aqc.model.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quiz_state")
public class QuizState extends CreateUpdateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_seq")
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_seq")
    private QuizStateType quizStateType;

    public void updateQuizStateType(QuizStateType quizStateType) {
        this.quizStateType = quizStateType;
    }

    @Builder
    public QuizState(Integer seq, User user, Quiz quiz, QuizStateType quizStateType) {
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
