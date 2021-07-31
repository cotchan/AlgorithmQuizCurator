package com.yhcy.aqc.model.quiz;

import com.yhcy.aqc.model.CreateTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "quiz_state_type")
public class QuizStateType extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(nullable = false, name = "desc")
    private String desc;

    @Column(nullable = false, name = "desc_kor")
    private String descKor;

    @Column(nullable = false, name = "state")
    private String state;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("seq", seq)
            .append("desc", desc)
            .append("desc_kor", descKor)
            .append("state", state)
            .toString();
    }
}
