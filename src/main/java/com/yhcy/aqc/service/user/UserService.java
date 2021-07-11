package com.yhcy.aqc.service.user;

import com.yhcy.aqc.model.user.Role;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.model.user.UserPassword;
import com.yhcy.aqc.model.user.VerifyQuestion;
import com.yhcy.aqc.repository.user.UserPasswordRepository;
import com.yhcy.aqc.repository.user.UserRepository;
import com.yhcy.aqc.repository.user.VerifyQuestionRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final UserPasswordRepository userPwRepo;
    private final VerifyQuestionRepository vqRepo;

    public UserService(UserRepository userRepo, UserPasswordRepository userPwRepo, VerifyQuestionRepository vqRepo) {
        this.userRepo = userRepo;
        this.userPwRepo = userPwRepo;
        this.vqRepo = vqRepo;
    }

    @Transactional
    public void joinService(UserVO user) throws Exception {
        //중복된 아이디 제한
        User dupTest = userRepo.findByUserId(user.getId());
        if (dupTest != null)
            throw new UnexpectedParamException("duplicated user ID ["+user.getId()+"]");
        
        //중복된 닉네임 제한
        User dupTest2 = userRepo.findByNickname(user.getNickname());
        if (dupTest2 != null)
            throw new UnexpectedParamException("duplicated user nickname ["+user.getNickname()+"]");

        //인증 질문 변조 확인
        int vqSeq;
        try {
            vqSeq = Integer.parseInt(user.getVerifyQuestion());
        } catch (Exception e) {
            throw new UnexpectedParamException("invalid verify question index ["+user.getVerifyQuestion()+"]");
        }
        VerifyQuestion vq = vqRepo.findBySeq(vqSeq);
        if (vq == null)
            throw new UnexpectedParamException("invalid verify question index ["+vq+"]");

        //비밀번호 해싱
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encodedPassword = pe.encode(user.getPw());
        System.out.println(encodedPassword);

        //비밀번호 확인
        //System.out.println(pe.matches(user.getPw(), encodedPassword));

        //insert into table using JPA
        User newUser = User.builder()
                .seq(null)
                .userId(user.getId())
                .nickname(user.getNickname())
                .verifyQuestion(vq)
                .verifyAnswer(user.getVerifyAnswer())
                .role(Role.USER)
                .build();
        User resUser = userRepo.save(newUser);

        UserPassword newUserPassword = UserPassword.builder()
                .seq(null)
                .user(resUser)
                .password(encodedPassword)
                .build();
        userPwRepo.saveAndFlush(newUserPassword);
    }

}
