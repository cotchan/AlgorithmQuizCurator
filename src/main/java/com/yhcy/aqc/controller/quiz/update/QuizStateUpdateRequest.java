package com.yhcy.aqc.controller.quiz.update;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yhcy.aqc.service.quiz.QuizStateForUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class QuizStateUpdateRequest {

    List<QuizStateForUpdate> quizStates;

    public QuizStateUpdateRequest(List<QuizStateForUpdate> quizStates) {
        this.quizStates = quizStates;
    }
}
