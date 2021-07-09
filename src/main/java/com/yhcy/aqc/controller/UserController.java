package com.yhcy.aqc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @GetMapping("/user/join")
    public String join() {
        return "/dev-test/user/join.html";
    }

    @ResponseBody
    @PostMapping("/user/join")
    public String joinProcess(String id, String pw, @RequestParam("pw_confirm") String pwConfirm, String nickname,
                              @RequestParam("verify_question") String verifyQuestion,
                              @RequestParam("verify_answer") String verifyAnswer) {
        System.out.println(id);
        System.out.println(pw);
        System.out.println(pwConfirm);
        System.out.println(nickname);
        System.out.println(verifyQuestion);
        System.out.println(verifyAnswer);
        return "success";
    }
}
