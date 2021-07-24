package com.yhcy.aqc.model.ranking;

import com.yhcy.aqc.model.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RankingListElement {
    private User user;
    private Long solvedCnt;
    private Double accuracyRatio;

    @Builder
    public RankingListElement(User user, Long solvedCnt, Double accuracyRatio) {
        this.user = user;
        this.solvedCnt = solvedCnt;
        this.accuracyRatio = accuracyRatio;
    }
}
