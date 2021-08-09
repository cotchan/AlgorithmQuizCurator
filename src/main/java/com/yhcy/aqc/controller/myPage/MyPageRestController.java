package com.yhcy.aqc.controller.myPage;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.controller.myPage.dto.QuizLogResponse;
import com.yhcy.aqc.model.quiz.QuizLog;
import com.yhcy.aqc.security.JwtAuthentication;
import com.yhcy.aqc.service.quiz.dao.QuizLogDaoService;
import com.yhcy.aqc.service.quiz.dao.QuizStateDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/myPage")
public class MyPageRestController {

    private final QuizStateDaoService stateService;
    private final QuizLogDaoService logService;

    @Async
    @GetMapping("solved-problems/")
    public CompletableFuture<ApiResult<List<QuizLogResponse>>> getSolvedProblems(@AuthenticationPrincipal JwtAuthentication authentication) {
        List<QuizLogResponse> results = stateService.getFindAllSolvingProblems(authentication.userId).stream().map(QuizLogResponse::new).collect(Collectors.toList());
        return CompletableFuture.completedFuture(ApiResult.OK(results));
    }

    @Async
    @GetMapping("solved-dates/")
    public CompletableFuture<ApiResult<List<QuizLogResponse>>> getQuizLogs(@AuthenticationPrincipal JwtAuthentication authentication,
                                                                @RequestParam("page-size") String pageSizeStr,
                                                                @RequestParam("page-no") String pageNoStr) {
        int pageSize = Integer.parseInt(pageSizeStr);
        int pageNo = Integer.parseInt(pageNoStr);
        List<QuizLog> logs = logService.getQuizLogsByUserId(authentication.userId, pageSize, pageNo);
        List<QuizLogResponse> res = new ArrayList<>();
        for (QuizLog log : logs) {
            res.add(new QuizLogResponse(log));
        }
        return CompletableFuture.completedFuture(ApiResult.OK(res));
    }
}
