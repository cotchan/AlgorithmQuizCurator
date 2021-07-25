package com.yhcy.aqc.service.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuizStateForUpdate {

    @JsonProperty(value="quiz_number")
    private int quizNumber;

    @JsonProperty(value="state_type")
    private int quizStateTypeCode;

    public QuizStateForUpdate(int quizNumber, int quizStateTypeCode) {
        this.quizNumber = quizNumber;
        this.quizStateTypeCode = quizStateTypeCode;
    }
}
