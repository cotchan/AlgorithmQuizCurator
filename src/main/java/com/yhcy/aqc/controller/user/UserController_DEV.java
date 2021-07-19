package com.yhcy.aqc.controller.user;

import com.yhcy.aqc.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController_DEV {

    private final UserService userService;

    public UserController_DEV(UserService userService) {
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

}
