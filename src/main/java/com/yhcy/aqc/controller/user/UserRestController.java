package com.yhcy.aqc.controller.user;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.controller.user.dto.JoinRequest;
import com.yhcy.aqc.controller.user.dto.ModRequest;
import com.yhcy.aqc.controller.user.dto.UserInfoResponse;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.security.JwtAuthentication;
import com.yhcy.aqc.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

import static com.yhcy.aqc.controller.common.ApiResult.OK;
import static com.yhcy.aqc.controller.user.dto.UserInfoResponse.fromUser;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserRestController {

    private final UserService userService;

    @Async
    @PostMapping("join")
    public CompletableFuture<ApiResult<Void>> joinProcess(@RequestBody JoinRequest newUser) {
        userService.join(newUser);
        return CompletableFuture.completedFuture(ApiResult.OK(null));
    }

    @Async
    @GetMapping("info/{userId}")
    public CompletableFuture<ApiResult<UserInfoResponse>> getInfoProcess(@PathVariable("userId") String userId) {
        User user = userService.getInfo(userId);
        UserInfoResponse userInfoResponse = new UserInfoResponse(user);
        return CompletableFuture.completedFuture(ApiResult.OK(userInfoResponse));
    }

    @Async
    @PostMapping("info")
    public CompletableFuture<ApiResult<Void>> modInfoProcess(@RequestBody ModRequest modUser) {
        userService.mod(modUser);
        return CompletableFuture.completedFuture(ApiResult.OK(null));
    }

    //FIXME: 삭제 예정
    @GetMapping(path = "me")
    public ApiResult<UserInfoResponse> me(@AuthenticationPrincipal JwtAuthentication authentication) throws Exception {
        return OK(fromUser(userService.getInfo(authentication.userId)));
    }
}
