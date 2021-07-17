package com.yhcy.aqc.service.user;

import com.yhcy.aqc.exception.UnexpectedParamException;
import com.yhcy.aqc.model.user.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public UserVO(String id, String pw, String pwConfirm, String nickname,
                  String verifyQuestion, String verifyAnswer) throws UnexpectedParamException {
        //영문(소문자)으로 시작하고 영문(소문자), 숫자를 포함하여 5~20자 제한
        if (id == null || !id.matches("^[a-z][a-z|_|\\\\-|0-9]{4,19}$"))
            throw new UnexpectedParamException("invalid user ID ["+id+"]");
        this.id = id;

        //8자 이상 영문, 숫자, 특문 모두 포함하도록 제한
        if (pw == null || !pw.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"))
            throw new UnexpectedParamException("invalid user password");
        this.pw = pw;

        //비밀번호와 비밀번호 확인 값이 같은지 확인
        if (pwConfirm == null || !pw.equals(pwConfirm))
            throw new UnexpectedParamException("mismatched password confirm");
        this.pwConfirm = pwConfirm;

        //닉네임은 2글자 이상으로 제한
        if (nickname == null || nickname.trim().length() < 2)
            throw new UnexpectedParamException("invalid user nickname ["+nickname+"]");
        this.nickname = nickname;

        this.verifyQuestion = verifyQuestion;

        //인증 질문 답변은 1글자 이상으로 제한
        if (verifyAnswer == null || verifyAnswer.trim().length() == 0)
            throw new UnexpectedParamException("invalid verify question answer ["+verifyAnswer+"]");
        this.verifyAnswer = verifyAnswer;

        this.roleCode = Role.USER;      //사용자 식별방식이 정해질 필요 있음 접근 시 유저 권한을 어떻게 식별할것인지? JWT?
    }
}
