package com.yhcy.aqc.controller.ranking.dto;

import com.yhcy.aqc.model.ranking.RankingListElement;
import lombok.Getter;

@Getter
public class AccuracyRankingResponse {
    private final String userId;
    private final String nickname;
    private final Double accuracyRatio;

    public AccuracyRankingResponse(RankingListElement rle) {
        this.userId = rle.getUser().getUserId();
        this.nickname = rle.getUser().getNickname();
        this.accuracyRatio = rle.getAccuracyRatio();
    }
}
