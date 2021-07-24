package com.yhcy.aqc.controller.myPage;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.model.quiz.QuizLog;
import com.yhcy.aqc.service.quiz.QuizLogService;
import com.yhcy.aqc.service.quiz.QuizStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/problem")
public class MyPageRestController {

    private final QuizStateService stateService;
    private final QuizLogService logService;

    @GetMapping("solved-problems/{userId}")
    public ApiResult<?> getSolvedProblems(@PathVariable("userId") String userId) {
        try {
            List<String> stateTypes = new ArrayList<>();
            stateTypes.add("not_solved");
            stateTypes.add("solved");
            //TODO : 실제 API 구현 시 DTO를 반환하도록 바꿔야함!
            return ApiResult.OK(stateService.getQuizByStatesAndUserId(stateTypes, userId, true));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("solved-dates/{userId}")
    public ApiResult<?> getQuizLogs(@PathVariable("userId") String userId) {
        try {
            List<QuizLog> logs = logService.getQuizLogsByUserId(userId);
            List<QuizLogResponse> res = new ArrayList<>();
            for (QuizLog log : logs) {
                String regdateStr = log.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd a hh:mm:ss"));
                QuizLogResponse qlr = QuizLogResponse.builder()
                        .userId(log.getUser().getUserId())
                        .nickname(log.getUser().getNickname())
                        .quizTitle(log.getQuiz().getTitle())
                        .quizURL(log.getQuiz().getRefSiteUrl())
                        .quizURLDesc(log.getQuiz().getRefSiteDesc())
                        //TODO: 추후 descKor 가져오도록 수정해야함
                        .quizStateDesc(log.getQuizStateType().getDesc())
                        .regdate(regdateStr)
                        .build();
                res.add(qlr);
            }
            return ApiResult.OK(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
