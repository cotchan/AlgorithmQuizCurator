package com.yhcy.aqc.controller.myPage;

import lombok.Builder;
import lombok.Getter;

@Getter
public class QuizLogResponse {
    private String userId;
    private String nickname;
    private String quizTitle;
    private String quizURL;
    private String quizURLDesc;
    private String quizStateDesc;
    private String regdate;

    @Builder
    public QuizLogResponse(String userId, String nickname, String quizTitle, String quizURL,
                           String quizURLDesc, String quizStateDesc, String regdate) {
        this.userId = userId;
        this.nickname = nickname;
        this.quizTitle = quizTitle;
        this.quizURL = quizURL;
        this.quizURLDesc = quizURLDesc;
        this.quizStateDesc = quizStateDesc;
        this.regdate = regdate;
    }
}
