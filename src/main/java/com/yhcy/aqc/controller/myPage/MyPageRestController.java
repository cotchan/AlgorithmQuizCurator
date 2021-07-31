package com.yhcy.aqc.controller.myPage;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.model.quiz.QuizLog;
import com.yhcy.aqc.model.quiz.QuizState;
import com.yhcy.aqc.model.quiz.QuizStateTypeEnum;
import com.yhcy.aqc.service.quiz.QuizLogService;
import com.yhcy.aqc.service.quiz.QuizStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/myPage")
public class MyPageRestController {

    private final QuizStateService stateService;
    private final QuizLogService logService;

    @Async
    @GetMapping("solved-problems/{userId}")
    public CompletableFuture<ApiResult<List<QuizLogResponse>>> getSolvedProblems(@PathVariable("userId") String userId) {
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
        return CompletableFuture.completedFuture(ApiResult.OK(res));
    }

    @Async
    @GetMapping("solved-dates/{userId}")
    public CompletableFuture<ApiResult<List<QuizLogResponse>>> getQuizLogs(@PathVariable("userId") String userId,
                                                                @RequestParam("page-size") String pageSizeStr,
                                                                @RequestParam("page-no") String pageNoStr) {
        int pageSize = Integer.parseInt(pageSizeStr);
        int pageNo = Integer.parseInt(pageNoStr);
        List<QuizLog> logs = logService.getQuizLogsByUserId(userId, pageSize, pageNo);
        List<QuizLogResponse> res = new ArrayList<>();
        for (QuizLog log : logs) {
            res.add(new QuizLogResponse(log));
        }
        return CompletableFuture.completedFuture(ApiResult.OK(res));
    }
}
