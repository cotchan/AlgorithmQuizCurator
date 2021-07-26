package com.yhcy.aqc.controller.user;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.error.NotFoundException;
import com.yhcy.aqc.error.UnexpectedParamException;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.security.JwtAuthentication;
import com.yhcy.aqc.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.yhcy.aqc.controller.common.ApiResult.OK;
import static com.yhcy.aqc.controller.user.UserInfoResponse.fromUser;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserRestController {

    private final UserService userService;

    @PostMapping("join")
    public ApiResult<?> joinProcess(@RequestBody JoinRequest newUser) {
        try {
            userService.join(newUser);
            return ApiResult.OK("success");
        } catch (UnexpectedParamException e) {
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("info/{userId}")
    public ApiResult<?> getInfoProcess(@PathVariable("userId") String userId) {
        try {
            User user = userService.getInfo(userId);

            UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                    .id(userId)
                    .nickname(user.getNickname())
                    .verifyQuestion(user.getVerifyQuestion().getDesc())
                    .verifyAnswer(user.getVerifyAnswer())
                    .build();

            return ApiResult.OK(userInfoResponse);
        } catch (UnexpectedParamException e) {
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("info")
    public ApiResult<?> modInfoProcess(@RequestBody ModRequest modUser) {
        try {
            userService.mod(modUser);
            return ApiResult.OK("success");
        } catch (UnexpectedParamException e) {
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //FIXME: 삭제 예정
    @GetMapping(path = "me")
    public ApiResult<UserInfoResponse> me(@AuthenticationPrincipal JwtAuthentication authentication) {
        return OK(
                userService.findById(authentication.seq)
                        .map(findUser -> fromUser(findUser))
                        .orElseThrow(() -> new NotFoundException(User.class, authentication.seq))
        );
    }
}
