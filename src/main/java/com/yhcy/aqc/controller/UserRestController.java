package com.yhcy.aqc.controller;

import com.yhcy.aqc.exception.UnexpectedParamException;
import com.yhcy.aqc.service.user.UserDTO;
import com.yhcy.aqc.service.user.UserService;
import com.yhcy.aqc.service.user.UserVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public String joinProcess(String id, String pw, @RequestParam("pw_confirm") String pwConfirm, String nickname,
                              @RequestParam("verify_question") String verifyQuestion,
                              @RequestParam("verify_answer") String verifyAnswer) {
        UserVO newUser = UserVO.builder()
                .id(id)
                .pw(pw)
                .pwConfirm(pwConfirm)
                .nickname(nickname)
                .verifyQuestion(verifyQuestion)
                .verifyAnswer(verifyAnswer)
                .build();

        try {
            userService.join(newUser);
        } catch (UnexpectedParamException e) {
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "unhandled error";
        }

        return "success";
    }

    @GetMapping("/info/{userId}")
    public UserDTO getInfoProcess(@PathVariable("userId") String userId) {
        try {
            return userService.getInfo(userId);
        } catch (UnexpectedParamException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/info")
    public String modInfoProcess(String id, String pw, @RequestParam("pw_confirm") String pwConfirm, String nickname,
                                 @RequestParam("verify_question") String verifyQuestion,
                                 @RequestParam("verify_answer") String verifyAnswer) {
        UserVO modUser = UserVO.builder()
                .id(id)
                .pw(pw)
                .pwConfirm(pwConfirm)
                .nickname(nickname)
                .verifyQuestion(verifyQuestion)
                .verifyAnswer(verifyAnswer)
                .build();

        try {
            userService.mod(modUser);
        } catch (UnexpectedParamException e) {
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "unhandled error";
        }

        return "success";
    }
}
