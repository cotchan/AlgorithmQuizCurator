package com.yhcy.aqc.controller.quiz;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.controller.quiz.pick.QuizPickRequest;
import com.yhcy.aqc.controller.quiz.pick.QuizPickResponse;
import com.yhcy.aqc.controller.quiz.update.QuizStateUpdateRequest;
import com.yhcy.aqc.controller.quiz.update.QuizStateUpdateResult;
import com.yhcy.aqc.security.JwtAuthentication;
import com.yhcy.aqc.service.quiz.QuizPickService;
import com.yhcy.aqc.service.quiz.QuizCheckService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.yhcy.aqc.controller.common.ApiResult.OK;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api/problems")
@RequiredArgsConstructor
public class QuizRestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final QuizPickService quizPickService;
    private final QuizCheckService quizCheckService;

    @Async
    @PostMapping(value = "pick")
    public CompletableFuture<ApiResult<List<QuizPickResponse>>> pickProblems(@AuthenticationPrincipal JwtAuthentication authentication, @RequestBody QuizPickRequest quizPickRequest) {

        final int problemCnt = NumberUtils.toInt(quizPickRequest.getProblemCnt(), 0);
        return CompletableFuture.completedFuture(
            OK(quizPickService.pickRandomProblems(authentication.seq, problemCnt)
                    .stream()
                    .map(QuizPickResponse::new)
                    .collect(toList())
        ));
    }

    @Async
    @PutMapping(value = "solved-check")
    public CompletableFuture<ApiResult<List<QuizStateUpdateResult>>> updateQuizState(@AuthenticationPrincipal JwtAuthentication authentication, @RequestBody QuizStateUpdateRequest updateRequest) {

        return CompletableFuture.completedFuture(
            OK(quizCheckService.update(authentication.seq, updateRequest.getQuizStates())
                    .stream()
                    .map(QuizStateUpdateResult::new)
                    .collect(toList())
        ));
    }
}
