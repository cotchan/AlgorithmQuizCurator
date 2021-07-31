package com.yhcy.aqc.controller.ranking;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.model.ranking.RankingListElement;
import com.yhcy.aqc.service.quiz.dao.QuizStateDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/ranking")
public class RankingRestController {

    private final QuizStateDaoService quizStateDaoService;

    @Async
    @GetMapping("by-solved")
    public CompletableFuture<ApiResult<List<SolvedRankingResponse>>> getRankingBySolved(@RequestParam("page-size") String pageSizeStr,
                                                                                        @RequestParam("page-no") String pageNoStr) {
        int pageSize = Integer.parseInt(pageSizeStr);
        int pageNo = Integer.parseInt(pageNoStr);
        List<RankingListElement> rankingList = quizStateDaoService.findBySolvedQuantity(pageSize, pageNo);
        List<SolvedRankingResponse> res = new ArrayList<>();
        for (RankingListElement rle : rankingList) {
            res.add(new SolvedRankingResponse(rle));
        }
        return CompletableFuture.completedFuture(ApiResult.OK(res));
    }

    @Async
    @GetMapping("by-accuracy")
    public CompletableFuture<ApiResult<List<AccuracyRankingResponse>>> getRankingByAccuracy(@RequestParam("page-size") String pageSizeStr,
                                                                                            @RequestParam("page-no") String pageNoStr) {
        int pageSize = Integer.parseInt(pageSizeStr);
        int pageNo = Integer.parseInt(pageNoStr);
        List<RankingListElement> rankingList = quizStateDaoService.findByAccuracy(pageSize, pageNo);
        List<AccuracyRankingResponse> res = new ArrayList<>();
        for (RankingListElement rle : rankingList) {
            res.add(new AccuracyRankingResponse(rle));
        }
        return CompletableFuture.completedFuture(ApiResult.OK(res));
    }
}
