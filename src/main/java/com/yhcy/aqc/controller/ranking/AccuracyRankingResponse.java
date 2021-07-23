package com.yhcy.aqc.controller.ranking;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccuracyRankingResponse {
    private String userId;
    private String nickname;
    private Double accuracyRatio;

    @Builder
    public AccuracyRankingResponse(String userId, String nickname, Double accuracyRatio) {
        this.userId = userId;
        this.nickname = nickname;
        this.accuracyRatio = accuracyRatio;
    }

}
