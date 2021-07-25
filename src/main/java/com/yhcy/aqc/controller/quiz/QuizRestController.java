package com.yhcy.aqc.controller.quiz;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.controller.quiz.pick.QuizPickRequest;
import com.yhcy.aqc.controller.quiz.pick.QuizPickResult;
import com.yhcy.aqc.controller.quiz.update.QuizStateUpdateRequest;
import com.yhcy.aqc.controller.quiz.update.QuizStateUpdateResult;
import com.yhcy.aqc.security.JwtAuthentication;
import com.yhcy.aqc.service.quiz.QuizPickService;
import com.yhcy.aqc.service.quiz.QuizService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yhcy.aqc.controller.common.ApiResult.OK;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api/problems")
@RequiredArgsConstructor
public class QuizRestController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final QuizPickService quizPickService;
    private final QuizService quizService;

    @PostMapping(value = "pick")
    public ApiResult<List<QuizPickResult>> pickProblems(@AuthenticationPrincipal JwtAuthentication authentication, @RequestBody QuizPickRequest quizPickRequest) throws Exception {
        final int problemCnt = NumberUtils.toInt(quizPickRequest.getProblemCnt(), 0);
        return OK(
                quizPickService.pickRandomProblems(authentication.seq, problemCnt).stream().map(QuizPickResult::new).collect(toList())
        );
    }

    @PutMapping(value = "solved_check")
    public ApiResult<List<QuizStateUpdateResult>> updateQuizState(@AuthenticationPrincipal JwtAuthentication authentication, @RequestBody QuizStateUpdateRequest updateRequest) throws Exception {

        /**
         * throws NotFoundException, IllegalArgumentException
         */
        return OK(
                quizService.update(authentication.seq, updateRequest.getQuizStates()).stream().map(QuizStateUpdateResult::new).collect(toList())
        );
    }
}
