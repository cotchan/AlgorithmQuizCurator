package com.yhcy.aqc.controller.myPage;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.service.quiz.QuizStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/problem")
public class MyPageRestController {

    private final QuizStateService stateService;

    @GetMapping("solved-problems/{userId}")
    public ApiResult<?> getSolvedProblems(@PathVariable("userId") String userId) {
        try {
            List<String> stateTypes = new ArrayList<>();
            stateTypes.add("not_solved");
            stateTypes.add("solved");
            //TODO : 실제 API 구현 시 DTO를 반환하도록 바꿔야함!
            return ApiResult.OK(stateService.getQuizByStatesAndUserId(stateTypes, userId, true));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
