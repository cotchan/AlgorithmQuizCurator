package com.yhcy.aqc.model.quiz;

import com.yhcy.aqc.model.BaseTimeEntity;
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
@Table(name = "quiz")
public class Quiz extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(nullable = false, name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "level")
    private QuizLevel quizLevel;

    @Column(nullable = false, name = "ref_site_url")
    private String refSiteUrl;

    @Column(nullable = false, name = "ref_site_desc")
    private String refSiteDesc;

    @Builder
    public Quiz(int seq, String title, QuizLevel quizLevel, String refSiteUrl, String refSiteDesc) {
        this.seq = seq;
        this.title = title;
        this.quizLevel = quizLevel;
        this.refSiteUrl = refSiteUrl;
        this.refSiteDesc = refSiteDesc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("seq", seq)
                .append("title", title)
                .append("quizLevel", quizLevel.value())
                .append("refSiteUrl", refSiteUrl)
                .append("refSiteDesc", refSiteDesc)
                .toString();
    }


}
