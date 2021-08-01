package com.yhcy.aqc.model.quiz;

import com.yhcy.aqc.model.CreateTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "quiz")
public class Quiz extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(nullable = false, name = "number")
    private Integer number;

    @Column(nullable = false, name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "level")
    private QuizLevel quizLevel;

    @Column(nullable = false, name = "ref_site_url")
    private String refSiteUrl;

    @Column(nullable = false, name = "ref_site_desc")
    private String refSiteDesc;

    /**
     * Quiz <=> QuizTag 사이를 양방향 매핑으로 바꾸면서 생긴 new field
     *
     * 현재는 QuizState에서 Quiz를 통해 QuizTagType에 접근하기 위한 용도로만 사용한다.
     * Quiz 단독으로 사용되는 쿼리에는 fetch join을 걸어놓지 않음
     */
    @OneToMany(mappedBy = "quiz")
    private List<QuizTag> quizTags = new ArrayList<>();

    @Builder
    public Quiz(int seq, int number, String title, QuizLevel quizLevel, String refSiteUrl, String refSiteDesc) {
        this.seq = seq;
        this.number = number;
        this.title = title;
        this.quizLevel = quizLevel;
        this.refSiteUrl = refSiteUrl;
        this.refSiteDesc = refSiteDesc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("seq", seq)
            .append("number", number)
            .append("title", title)
            .append("quizLevel", quizLevel.value())
            .append("refSiteUrl", refSiteUrl)
            .append("refSiteDesc", refSiteDesc)
            .toString();
    }
}
