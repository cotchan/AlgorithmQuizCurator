package com.yhcy.aqc.model.quiz;

import com.yhcy.aqc.model.BaseTimeEntity;
import com.yhcy.aqc.model.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quiz_state")
public class QuizState extends BaseTimeEntity {

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
}
