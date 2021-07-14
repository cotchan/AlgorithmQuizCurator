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

import java.util.List;
import java.util.Optional;

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
    public void joinService(UserVO userVO) throws Exception {
        //중복된 아이디 제한
        Optional<User> dupTest = userRepo.findByUserId(userVO.getId());
        if (dupTest.isPresent())
            throw new UnexpectedParamException("duplicated user ID ["+userVO.getId()+"]");
        
        //중복된 닉네임 제한
        Optional<User> dupTest2 = userRepo.findByNickname(userVO.getNickname());
        if (dupTest2.isPresent())
            throw new UnexpectedParamException("duplicated user nickname ["+userVO.getNickname()+"]");

        //인증 질문 변조 확인
        int vqSeq;
        try {
            vqSeq = Integer.parseInt(userVO.getVerifyQuestion());
        } catch (Exception e) {
            throw new UnexpectedParamException("invalid verify question index ["+userVO.getVerifyQuestion()+"]");
        }
        Optional<VerifyQuestion> vq = vqRepo.findBySeq(vqSeq);
        if (!vq.isPresent())
            throw new UnexpectedParamException("invalid verify question index");

        //비밀번호 해싱
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encodedPassword = pe.encode(userVO.getPw());
        //System.out.println(encodedPassword);

        User newUser = User.builder()
                .seq(null)
                .userId(userVO.getId())
                .nickname(userVO.getNickname())
                .verifyQuestion(vq.get())
                .verifyAnswer(userVO.getVerifyAnswer())
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

    @Transactional
    public void modService(UserVO userVO) throws Exception {
        //인증 질문 변조 확인
        int vqSeq;
        try {
            vqSeq = Integer.parseInt(userVO.getVerifyQuestion());
        } catch (Exception e) {
            throw new UnexpectedParamException("invalid verify question index ["+userVO.getVerifyQuestion()+"]");
        }
        Optional<VerifyQuestion> vq = vqRepo.findBySeq(vqSeq);
        if (!vq.isPresent())
            throw new UnexpectedParamException("invalid verify question index");

        //유저 조회 후 ID, 닉네임, 비밀번호를 제외한 정보 수정
        Optional<User> user = userRepo.findByUserId(userVO.getId());
        if (!user.isPresent())
            throw new UnexpectedParamException("user ID not found");
        user.ifPresent(selectUser -> {
            selectUser.setVerifyQuestion(vq.get());
            selectUser.setVerifyAnswer(userVO.getVerifyAnswer());
        });
        userRepo.save(user.get());

        //비밀번호 해싱
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encodedPassword = pe.encode(userVO.getPw());
        //이전에 사용했던 비밀번호인지 확인
        List<UserPassword> userPasswords = userPwRepo.findByUser(user.get());
        for (UserPassword up : userPasswords) {
            if (pe.matches(userVO.getPw(), up.getPassword()))
                throw new UnexpectedParamException("user password that have been used before");
        }

        UserPassword newUserPassword = UserPassword.builder()
                .seq(null)
                .user(user.get())
                .password(encodedPassword)
                .build();
        userPwRepo.saveAndFlush(newUserPassword);
    }

}
