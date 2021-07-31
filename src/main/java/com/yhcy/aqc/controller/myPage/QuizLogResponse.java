package com.yhcy.aqc.controller.myPage;

import com.yhcy.aqc.model.quiz.QuizLog;
import com.yhcy.aqc.model.quiz.QuizState;
import com.yhcy.aqc.model.quiz.QuizStateTypeEnum;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class QuizLogResponse {
    private final String userId;
    private final String nickname;
    private final int quizNumber;
    private final String quizTitle;
    private final String quizURL;
    private final String quizURLDesc;
    private final String quizLevel;
    private final int quizStateCode;
    private final String quizStateDesc;
    private final String regdate;

    public QuizLogResponse(QuizLog log) {
        this.userId = log.getUser().getUserId();
        this.nickname = log.getUser().getNickname();
        this.quizNumber = log.getQuiz().getNumber();
        this.quizTitle = log.getQuiz().getTitle();
        this.quizURL = log.getQuiz().getRefSiteUrl();
        this.quizURLDesc = log.getQuiz().getRefSiteDesc();
        this.quizLevel = log.getQuiz().getQuizLevel().value();
        this.quizStateCode = QuizStateTypeEnum.toCode(log.getQuizStateType().getDesc());
        this.quizStateDesc = log.getQuizStateType().getDescKor();
        this.regdate = log.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd a hh:mm:ss"));
    }

    public QuizLogResponse(QuizState qs) {
        this.userId = qs.getUser().getUserId();
        this.nickname = qs.getUser().getNickname();
        this.quizNumber = qs.getQuiz().getNumber();
        this.quizTitle = qs.getQuiz().getTitle();
        this.quizURL = qs.getQuiz().getRefSiteUrl();
        this.quizURLDesc = qs.getQuiz().getRefSiteDesc();
        this.quizLevel = qs.getQuiz().getQuizLevel().value();
        this.quizStateCode = QuizStateTypeEnum.toCode(qs.getQuizStateType().getDesc());
        this.quizStateDesc = qs.getQuizStateType().getDescKor();
        this.regdate = qs.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd a hh:mm:ss"));
    }
}
