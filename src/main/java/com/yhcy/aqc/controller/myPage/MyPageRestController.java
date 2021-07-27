package com.yhcy.aqc.controller.myPage;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.model.quiz.QuizLog;
import com.yhcy.aqc.model.quiz.QuizState;
import com.yhcy.aqc.model.quiz.QuizStateTypeEnum;
import com.yhcy.aqc.service.quiz.QuizLogService;
import com.yhcy.aqc.service.quiz.QuizStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/myPage")
public class MyPageRestController {

    private final QuizStateService stateService;
    private final QuizLogService logService;

    @GetMapping("solved-problems/{userId}")
    public ApiResult<?> getSolvedProblems(@PathVariable("userId") String userId) {
        try {
            List<String> stateTypes = new ArrayList<>();
            stateTypes.add(QuizStateTypeEnum.NOT_SOLVED.desc());
            stateTypes.add(QuizStateTypeEnum.TIME_OVER.desc());
            stateTypes.add(QuizStateTypeEnum.TC_NOT_PASSED.desc());
            stateTypes.add(QuizStateTypeEnum.SOLVED.desc());
            List<QuizState> quizList = stateService.getQuizStatesByStatesAndUserId(stateTypes, userId);
            List<QuizLogResponse> res = new ArrayList<>();
            for (QuizState qs : quizList) {
                res.add(new QuizLogResponse(qs));
            }
            return ApiResult.OK(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("solved-dates/{userId}")
    public ApiResult<?> getQuizLogs(@PathVariable("userId") String userId,
                                    @RequestParam("page-size") String pageSizeStr,
                                    @RequestParam("page-no") String pageNoStr) {
        try {
            int pageSize = Integer.parseInt(pageSizeStr);
            int pageNo = Integer.parseInt(pageNoStr);
            List<QuizLog> logs = logService.getQuizLogsByUserId(userId, pageSize, pageNo);
            List<QuizLogResponse> res = new ArrayList<>();
            for (QuizLog log : logs) {
                res.add(new QuizLogResponse(log));
            }
            return ApiResult.OK(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
