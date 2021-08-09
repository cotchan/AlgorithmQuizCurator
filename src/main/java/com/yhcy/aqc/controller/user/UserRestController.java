package com.yhcy.aqc.controller.user;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.controller.user.dto.JoinRequest;
import com.yhcy.aqc.controller.user.dto.ModRequest;
import com.yhcy.aqc.controller.user.dto.UserInfoResponse;
import com.yhcy.aqc.controller.user.dto.VerifyQuestionResponse;
import com.yhcy.aqc.error.UnauthorizedException;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.model.user.VerifyQuestion;
import com.yhcy.aqc.security.JwtAuthentication;
import com.yhcy.aqc.service.user.UserService;
import com.yhcy.aqc.service.user.dao.UserDaoService;
import com.yhcy.aqc.service.user.dao.VerifyQuestionDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.yhcy.aqc.controller.common.ApiResult.OK;
import static com.yhcy.aqc.controller.user.dto.UserInfoResponse.fromUser;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserRestController {

    private final UserService userService;
    private final UserDaoService userDaoService;
    private final VerifyQuestionDaoService verifyQuestionDaoService;

    @Async
    @PostMapping("join")
    public CompletableFuture<ApiResult<Void>> joinProcess(@RequestBody JoinRequest newUser) {
        userService.join(newUser);
        return CompletableFuture.completedFuture(ApiResult.OK(null));
    }

    @Async
    @GetMapping("info")
    public CompletableFuture<ApiResult<UserInfoResponse>> getInfoProcess(@AuthenticationPrincipal JwtAuthentication authentication) {
        User user = userService.getInfo(authentication.userId);
        UserInfoResponse userInfoResponse = new UserInfoResponse(user);
        return CompletableFuture.completedFuture(ApiResult.OK(userInfoResponse));
    }

    @Async
    @PostMapping("info")
    public CompletableFuture<ApiResult<Void>> modInfoProcess(@AuthenticationPrincipal JwtAuthentication authentication,
                                                             @RequestBody ModRequest modUser) {
        userService.mod(authentication.userId, modUser);
        return CompletableFuture.completedFuture(ApiResult.OK(null));
    }

    @Async
    @GetMapping(value = "verify-questions")
    public CompletableFuture<ApiResult<List<VerifyQuestionResponse>>> getQuizStateTypes() {
        List<VerifyQuestion> quizStateTypes = verifyQuestionDaoService.findAll();
        List<VerifyQuestionResponse> result = new LinkedList<>();
        for (VerifyQuestion vq : quizStateTypes) {
            result.add(new VerifyQuestionResponse(vq.getSeq(), vq.getDesc()));
        }
        return CompletableFuture.completedFuture(OK(result));
    }

    @Async
    @GetMapping(value = "dup-check-by-userId/{userId}")
    public CompletableFuture<ApiResult<Boolean>> checkDupByUserId(@PathVariable("userId") String userId) {
        return CompletableFuture.completedFuture(OK(userDaoService.checkDupByUserId(userId)));
    }

    @Async
    @GetMapping(value = "dup-check-by-nickname/{nickname}")
    public CompletableFuture<ApiResult<Boolean>> checkDupByNickname(@PathVariable("nickname") String nickname) {
        return CompletableFuture.completedFuture(OK(userDaoService.checkDupByNickname(nickname)));
    }

    //FIXME: 삭제 예정
    @GetMapping(path = "me")
    public ApiResult<UserInfoResponse> me(@AuthenticationPrincipal JwtAuthentication authentication) throws Exception {
        return OK(fromUser(userService.getInfo(authentication.userId)));
    }
}
