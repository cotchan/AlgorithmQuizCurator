package com.yhcy.aqc.controller.quiz.pick;

import com.yhcy.aqc.model.quiz.QuizState;
import lombok.Getter;

@Getter
public class QuizPickResult {

    private final String quizTitle;
    private final String quizURL;
    private final String quizStateType;
    private final String quizLevel;

    public QuizPickResult(QuizState source) {
        this.quizTitle = source.getQuiz().getTitle();
        this.quizURL = source.getQuiz().getRefSiteUrl();
        this.quizStateType = source.getQuizStateType().getDescKor();
        this.quizLevel = source.getQuiz().getQuizLevel().value();
    }
}
