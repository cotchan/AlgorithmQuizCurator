package com.yhcy.aqc.model.quiz;

import com.yhcy.aqc.model.CreateTimeEntity;
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
@Table(name = "quiz_tag")
public class QuizTag extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_seq")
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_tag_type_seq")
    private QuizTagType quizTagType;

    @Builder
    public QuizTag(Integer seq, Quiz quiz, QuizTagType quizTagType) {
        this.seq = seq;
        this.quiz = quiz;
        this.quizTagType = quizTagType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("seq", seq)
                .append("quiz", quiz)
                .append("quizTagType", quizTagType)
                .toString();
    }
}
