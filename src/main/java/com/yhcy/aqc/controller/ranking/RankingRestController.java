package com.yhcy.aqc.controller.ranking;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.model.ranking.RankingListElement;
import com.yhcy.aqc.service.quiz.QuizStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/ranking")
public class RankingRestController {

    private final QuizStateService stateService;

    @GetMapping("by-solved")
    public ApiResult<?> getRankingBySolved() {
        try {
            List<RankingListElement> rankingList = stateService.getRankingBySolved();
            List<SolvedRankingResponse> res = new ArrayList<>();
            for (RankingListElement rle : rankingList) {
                SolvedRankingResponse srr = SolvedRankingResponse.builder()
                        .userId(rle.getUser().getUserId())
                        .nickname(rle.getUser().getNickname())
                        .solvedCnt(rle.getSolvedCnt())
                        .build();
                res.add(srr);
            }
            return ApiResult.OK(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("by-accuracy")
    public ApiResult<?> getRankingByAccuracy() {
        try {
            List<RankingListElement> rankingList = stateService.getRankingByAccuracy();
            List<AccuracyRankingResponse> res = new ArrayList<>();
            for (RankingListElement rle : rankingList) {
                AccuracyRankingResponse arr = AccuracyRankingResponse.builder()
                        .userId(rle.getUser().getUserId())
                        .nickname(rle.getUser().getNickname())
                        .accuracyRatio(rle.getAccuracyRatio())
                        .build();
                res.add(arr);
            }
            return ApiResult.OK(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
