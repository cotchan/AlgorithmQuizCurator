package com.yhcy.aqc.service.user;

import com.yhcy.aqc.model.user.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserVO {
    private String id;
    private String pw;
    private String pwConfirm;
    private String nickname;
    private String verifyQuestion;
    private String verifyAnswer;
    private Role roleCode;
    private LocalDateTime regdate;

    @Builder
    public UserVO(String id, String pw, String pwConfirm, String nickname,
                  String verifyQuestion, String verifyAnswer) {
        this.id = id;
        this.pw = pw;
        this.pwConfirm = pwConfirm;
        this.nickname = nickname;
        this.verifyQuestion = verifyQuestion;
        this.verifyAnswer = verifyAnswer;
        this.roleCode = Role.USER;      //사용자 식별방식이 정해질 필요 있음 접근 시 유저 권한을 어떻게 식별할것인지? JWT?
        this.regdate = LocalDateTime.now();
    }
}
