package com.yhcy.aqc.service.quiz;

import com.yhcy.aqc.model.ranking.RankingListElement;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.repository.quiz.QuizStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final QuizStateRepository stateRepo;

    public List<RankingListElement> getRankingBySolved() {
        List<Object[]> rankingList =  stateRepo.findBySolvedQuantity();

        List<RankingListElement> result = new ArrayList<>();
        for (Object[] rl : rankingList) {
            RankingListElement srr = RankingListElement.builder()
                    .user((User) rl[0])
                    .solvedCnt((Long) rl[1])
                    .build();
            result.add(srr);
        }

        return result;
    }

    public List<RankingListElement> getRankingByAccuracy() {
        List<Object[]> rankingList =  stateRepo.findByAccuracy();

        List<RankingListElement> result = new ArrayList<>();
        for (Object[] rl : rankingList) {
            RankingListElement arr = RankingListElement.builder()
                    .user((User) rl[0])
                    .accuracyRatio((Double) rl[1])
                    .build();
            result.add(arr);
        }

        return result;
    }
}
