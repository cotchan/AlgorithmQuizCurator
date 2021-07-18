package com.yhcy.aqc.controller.user;

import com.yhcy.aqc.controller.common.ApiResult;
import com.yhcy.aqc.error.NotFoundException;
import com.yhcy.aqc.model.user.Role;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.security.Jwt;
import com.yhcy.aqc.security.JwtAuthentication;
import com.yhcy.aqc.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.yhcy.aqc.controller.common.ApiResult.OK;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserRestController {

    private final Jwt jwt;
    private final UserService userService;

    @PostMapping(path = "user/join")
    public ResponseEntity<JoinResult> join(@RequestBody JoinRequest joinRequest) {

        User user = User.builder()
                .seq(1)
                .userId(joinRequest.getPrincipal())
                .nickName("zoro")
                .nickName(joinRequest.getCredentials())
                .build();

        String apiToken = user.newApiToken(jwt, new String[]{Role.USER.value()});
        return ResponseEntity.ok(new JoinResult(apiToken, new UserDto(user)));
    }

    @GetMapping(path = "user/me")
    public ApiResult<UserDto> me(@AuthenticationPrincipal JwtAuthentication authentication) {
        return OK(
                userService.findById(authentication.seq)
                        .map(UserDto::new)
                        .orElseThrow(() -> new NotFoundException(User.class, authentication.seq))
        );
    }
}
