package com.yhcy.aqc.controller.ranking;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.service.quiz.QuizStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/ranking")
public class RankingRestController {

    private final QuizStateService stateService;

    @GetMapping("by-solved")
    public ApiResult<?> getRankingBySolved() {
        try {
            return ApiResult.OK(stateService.getRankingBySolved());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("by-accuracy")
    public ApiResult<?> getRankingByAccuracy() {
        try {
            return ApiResult.OK(stateService.getRankingByAccuracy());
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
