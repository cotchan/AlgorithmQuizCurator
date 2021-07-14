package com.yhcy.aqc.controller;

import com.yhcy.aqc.service.user.UnexpectedParamException;
import com.yhcy.aqc.service.user.UserDTO;
import com.yhcy.aqc.service.user.UserService;
import com.yhcy.aqc.service.user.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/join")
    public String join() {
        return "/dev-test/user/join.html";
    }

    @GetMapping("/info2")
    public String info() {
        return "/dev-test/user/mod.html";
    }

    @ResponseBody
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

    @ResponseBody
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

    @ResponseBody
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
