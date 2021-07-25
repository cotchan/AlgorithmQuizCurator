package com.yhcy.aqc.controller.quiz.pick;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.security.JwtAuthentication;
import com.yhcy.aqc.service.quiz.QuizPickService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yhcy.aqc.controller.common.ApiResult.OK;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api/problems")
@RequiredArgsConstructor
public class QuizPickRestController {

    private final QuizPickService quizPickService;

    @GetMapping
    public int func() {
        quizPickService.testFunc();

        return 1;
    }

    @PostMapping(value = "pick")
    public ApiResult<List<QuizPickResult>> pickProblems(@AuthenticationPrincipal JwtAuthentication authentication, @RequestBody QuizPickRequest quizPickRequest) throws Exception {
        final int problemCnt = NumberUtils.toInt(quizPickRequest.getProblemCnt(), 0);
        return OK(
            quizPickService.pickRandomProblems(authentication.seq, problemCnt).stream().map(QuizPickResult::new).collect(toList())
        );
    }
}
