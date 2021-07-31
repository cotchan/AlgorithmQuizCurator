package com.yhcy.aqc.controller.quiz.pick;

import com.yhcy.aqc.model.quiz.QuizState;
import lombok.Getter;

@Getter
public class QuizPickResponse {

    private final String quizNumber;
    private final String quizTitle;
    private final String quizUrl;
    private final String quizUrlDesc;
    private final String quizStateType;
    private final String quizLevel;

    public QuizPickResponse(QuizState source) {
        this.quizNumber = String.valueOf(source.getQuiz().getNumber());
        this.quizTitle = source.getQuiz().getTitle();
        this.quizUrl = source.getQuiz().getRefSiteUrl();
        this.quizUrlDesc = source.getQuiz().getRefSiteDesc();
        this.quizStateType = source.getQuizStateType().getDescKor();
        this.quizLevel = source.getQuiz().getQuizLevel().value();
    }
}
