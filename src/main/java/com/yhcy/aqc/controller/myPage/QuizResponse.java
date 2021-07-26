package com.yhcy.aqc.controller.myPage;

import com.yhcy.aqc.model.quiz.Quiz;
import lombok.Getter;

@Getter
public class QuizResponse {
    private final String quizNumber;
    private final String quizTitle;
    private final String quizURL;
    private final String quizURLDesc;
    private final String quizLevel;

    public QuizResponse(Quiz quiz) {
        this.quizNumber = "" + quiz.getNumber();
        this.quizTitle = quiz.getTitle();
        this.quizURL = quiz.getRefSiteUrl();
        this.quizURLDesc = quiz.getRefSiteDesc();
        this.quizLevel = quiz.getQuizLevel().value();
    }
}
