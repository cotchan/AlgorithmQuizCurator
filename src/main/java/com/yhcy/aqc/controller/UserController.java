package com.yhcy.aqc.controller;

import com.yhcy.aqc.repository.user.UserPasswordRepository;
import com.yhcy.aqc.repository.user.UserRepository;
import com.yhcy.aqc.repository.user.VerifyQuestionRepository;
import com.yhcy.aqc.service.user.UnexpectedParamException;
import com.yhcy.aqc.service.user.UserService;
import com.yhcy.aqc.service.user.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/join")
    public String join() {
        return "/dev-test/user/join.html";
    }

    @ResponseBody
    @PostMapping("/user/join")
    public String joinProcess(String id, String pw, @RequestParam("pw_confirm") String pwConfirm, String nickname,
                              @RequestParam("verify_question") String verifyQuestion,
                              @RequestParam("verify_answer") String verifyAnswer) {
        UserVO newUser = UserVO.builder().id(id).pw(pw).pwConfirm(pwConfirm).nickname(nickname).
                verifyQuestion(verifyQuestion).verifyAnswer(verifyAnswer).build();

        try {
            userService.joinService(newUser);
        } catch (UnexpectedParamException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }
}
