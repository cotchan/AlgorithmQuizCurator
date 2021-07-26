package com.yhcy.aqc.controller.ranking;

import com.yhcy.aqc.model.ranking.RankingListElement;
import lombok.Getter;

@Getter
public class SolvedRankingResponse {
    private final String userId;
    private final String nickname;
    private final Long solvedCnt;

    public SolvedRankingResponse(RankingListElement rle) {
        this.userId = rle.getUser().getUserId();
        this.nickname = rle.getUser().getNickname();
        this.solvedCnt = rle.getSolvedCnt();
    }

}
