package com.yhcy.aqc.controller.quiz.pick;

import com.yhcy.aqc.model.quiz.Quiz;
import com.yhcy.aqc.model.quiz.QuizState;
import lombok.Getter;

@Getter
public class QuizPickResponse {

    private final String quizNumber;
    private final String quizTitle;
    private final String quizURL;
    private final String quizStateType;
    private final String quizLevel;

    public QuizPickResponse(QuizState source) {
        this.quizNumber = String.valueOf(source.getQuiz().getNumber());
        this.quizTitle = source.getQuiz().getTitle();
        this.quizURL = source.getQuiz().getRefSiteUrl();
        this.quizStateType = source.getQuizStateType().getDescKor();
        this.quizLevel = source.getQuiz().getQuizLevel().value();
    }
}
