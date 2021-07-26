package com.yhcy.aqc.controller.myPage;

import com.yhcy.aqc.model.quiz.QuizLog;
import com.yhcy.aqc.model.quiz.QuizStateTypeEnum;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class QuizLogResponse {
    private final String userId;
    private final String nickname;
    private final String quizTitle;
    private final String quizURL;
    private final String quizURLDesc;
    private final String quizStateCode;
    private final String quizStateDesc;
    private final String regdate;

    public QuizLogResponse(QuizLog log) {
        this.userId = log.getUser().getUserId();
        this.nickname = log.getUser().getNickname();
        this.quizTitle = log.getQuiz().getTitle();
        this.quizURL = log.getQuiz().getRefSiteUrl();
        this.quizURLDesc = log.getQuiz().getRefSiteDesc();
        this.quizStateCode = ""+QuizStateTypeEnum.toCode(log.getQuizStateType().getDesc());
        this.quizStateDesc = log.getQuizStateType().getDescKor();
        this.regdate = log.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd a hh:mm:ss"));
    }
}
