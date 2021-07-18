package com.yhcy.aqc.controller;

import com.yhcy.aqc.error.UnexpectedParamException;
import com.yhcy.aqc.service.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinRequest newUser) {
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
    public UserResponse getInfoProcess(@PathVariable("userId") String userId) {
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
    public String modInfoProcess(@RequestBody ModRequest modUser) {
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
