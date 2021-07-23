package com.yhcy.aqc.controller.ranking;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolvedRankingResponse {
    private String userId;
    private String nickname;
    private Long solvedCnt;

    @Builder
    public SolvedRankingResponse(String userId, String nickname, Long solvedCnt) {
        this.userId = userId;
        this.nickname = nickname;
        this.solvedCnt = solvedCnt;
    }

}
