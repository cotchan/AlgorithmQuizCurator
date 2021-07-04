package com.yhcy.aqc.model.quiz;

import com.yhcy.aqc.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quiz_tag")
public class QuizTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Integer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_seq")
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_tag_type_seq")
    private QuizTagType quizTagType;

    @Builder
    public QuizTag(java.lang.Integer integer, Quiz quiz, QuizTagType quizTagType) {
        Integer = integer;
        this.quiz = quiz;
        this.quizTagType = quizTagType;
    }
}
