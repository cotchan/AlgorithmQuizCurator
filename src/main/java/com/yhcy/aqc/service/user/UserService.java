package com.yhcy.aqc.service.user;

import com.yhcy.aqc.service.user.dao.UserDaoService;
import com.yhcy.aqc.service.user.dao.UserPasswordDaoService;
import com.yhcy.aqc.service.user.dao.VerifyQuestionDaoService;
import lombok.RequiredArgsConstructor;
import com.yhcy.aqc.controller.user.dto.ModRequest;
import com.yhcy.aqc.controller.user.dto.JoinRequest;
import com.yhcy.aqc.model.user.Role;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.model.user.UserPassword;
import com.yhcy.aqc.model.user.VerifyQuestion;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserDaoService userDaoService;
    private final UserPasswordDaoService userPasswordDaoService;
    private final VerifyQuestionDaoService verifyQuestionDaoService;

    public User getInfo(String userId) {
        return userDaoService.findByUserId(userId);
    }

    public void join(JoinRequest joinRequest) {
        //영문(소문자)으로 시작하고 영문(소문자), 숫자를 포함하여 5~20자 제한
        if (joinRequest.getId() == null || !joinRequest.getId().matches("^[a-z][a-z|_|\\\\-|0-9]{4,19}$")) {
            throw new IllegalArgumentException("invalid user ID [" + joinRequest.getId() + "]");
        }

        //8자 이상 영문, 숫자, 특문 모두 포함하도록 제한
        if (joinRequest.getPw() == null || !joinRequest.getPw().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")) {
            throw new IllegalArgumentException("invalid user password");
        }

        //비밀번호와 비밀번호 확인 값이 같은지 확인
        if (joinRequest.getPwConfirm() == null || !joinRequest.getPw().equals(joinRequest.getPwConfirm())) {
            throw new IllegalArgumentException("mismatched password confirm");
        }

        //닉네임은 2글자 이상으로 제한
        if (joinRequest.getNickname() == null || joinRequest.getNickname().trim().length() < 2) {
            throw new IllegalArgumentException("invalid user nickname ["+ joinRequest.getNickname()+"]");
        }

        if (joinRequest.getVerifyAnswer() == null || joinRequest.getVerifyAnswer().trim().length() == 0) {
            throw new IllegalArgumentException("invalid verify question answer ["+ joinRequest.getVerifyAnswer()+"]");
        }

        //중복된 아이디 제한
        if (userDaoService.checkDupByUserId(joinRequest.getId()))
            throw new IllegalArgumentException("duplicated user ID ["+ joinRequest.getId()+"]");

        //중복된 닉네임 제한
        if (userDaoService.checkDupByNickname(joinRequest.getNickname()))
            throw new IllegalArgumentException("duplicated user nickname ["+ joinRequest.getNickname()+"]");

        //인증 질문 변조 확인
        VerifyQuestion vq = verifyQuestionDaoService.findBySeq(joinRequest.getVerifyQuestion());

        //비밀번호 해싱
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encodedPassword = pe.encode(joinRequest.getPw());

        User newUser = User.builder()
                .seq(null)
                .userId(joinRequest.getId())
                .nickname(joinRequest.getNickname())
                .verifyQuestion(vq)
                .verifyAnswer(joinRequest.getVerifyAnswer())
                .role(Role.USER)
                .build();
        newUser = userDaoService.saveUser(newUser);

        UserPassword userPassword = UserPassword.builder()
                .seq(null)
                .user(newUser)
                .password(encodedPassword)
                .build();
        userPasswordDaoService.saveUserPassword(userPassword);
    }

    @Transactional
    public void mod(String userId, ModRequest modRequest) {
        //유저 조회 후 ID, 닉네임, 비밀번호를 제외한 정보 수정
        //인증 질문 변조 확인
        if (!(modRequest.getVerifyQuestion() == null || modRequest.getVerifyQuestion().trim().isEmpty())
        || !(modRequest.getVerifyAnswer() == null || modRequest.getVerifyAnswer().trim().isEmpty())) {
            VerifyQuestion vq = verifyQuestionDaoService.findBySeq(modRequest.getVerifyQuestion().trim());
            userDaoService.updateUserByUserId(userId, vq, modRequest.getVerifyAnswer().trim());
        }

        if (!(modRequest.getPw() == null || modRequest.getPw().trim().isEmpty())) {
            if (modRequest.getPwConfirm() == null || modRequest.getPwConfirm().trim().isEmpty())
                throw new IllegalArgumentException("empty user password confirm");
            if (!modRequest.getPw().trim().equals(modRequest.getPwConfirm().trim()))
                throw new IllegalArgumentException("user password and password confirm not matched");
            User user = userDaoService.findByUserId(userId);
            //비밀번호 해싱
            PasswordEncoder pe = new BCryptPasswordEncoder();
            String encodedPassword = pe.encode(modRequest.getPw().trim());
            //이전에 사용했던 비밀번호인지 확인
            List<UserPassword> userPasswords = userPasswordDaoService.findByUser(user);
            for (UserPassword up : userPasswords) {
                if (pe.matches(modRequest.getPw().trim(), up.getPassword()))
                    throw new IllegalArgumentException("user password that have been used before");
            }

            UserPassword newUserPassword = UserPassword.builder()
                    .seq(null)
                    .user(user)
                    .password(encodedPassword)
                    .build();
            userPasswordDaoService.saveUserPassword(newUserPassword);
        }
    }

    public User login(String userId, String password) {
        User user = userDaoService.findByUserId(userId);
        //비밀번호 매치 확인
        PasswordEncoder pe = new BCryptPasswordEncoder();
        List<UserPassword> userPasswords = userPasswordDaoService.findByUser(user);
        UserPassword userPassword = userPasswords.get(0);
        if (!pe.matches(password, userPassword.getPassword()))
            throw new IllegalArgumentException("user password not matched");
        return user;
    }
}
