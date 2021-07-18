package com.yhcy.aqc.service.user;

import lombok.RequiredArgsConstructor;
import com.yhcy.aqc.controller.ModRequest;
import com.yhcy.aqc.controller.UserResponse;
import com.yhcy.aqc.controller.JoinRequest;
import com.yhcy.aqc.error.UnexpectedParamException;
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


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepo;
    private final UserPasswordRepository userPwRepo;
    private final VerifyQuestionRepository vqRepo;

    public UserResponse getInfo(String userId) throws Exception {
        Optional<User> user = userRepo.findByUserId(userId);
        if (!user.isPresent())
            throw new UnexpectedParamException("user ID not found");

        UserResponse userResponse = UserResponse.builder()
                .id(userId)
                .nickname(user.get().getNickname())
                .verifyQuestion(user.get().getVerifyQuestion().getDesc())
                .verifyAnswer(user.get().getVerifyAnswer())
                .build();
        return userResponse;
    }

    @Transactional
    public void join(JoinRequest joinRequest) throws Exception {
        //영문(소문자)으로 시작하고 영문(소문자), 숫자를 포함하여 5~20자 제한
        if (joinRequest.getId() == null || !joinRequest.getId().matches("^[a-z][a-z|_|\\\\-|0-9]{4,19}$"))
            throw new UnexpectedParamException("invalid user ID ["+ joinRequest.getId()+"]");

        //8자 이상 영문, 숫자, 특문 모두 포함하도록 제한
        if (joinRequest.getPw() == null || !joinRequest.getPw().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"))
            throw new UnexpectedParamException("invalid user password");

        //비밀번호와 비밀번호 확인 값이 같은지 확인
        if (joinRequest.getPwConfirm() == null || !joinRequest.getPw().equals(joinRequest.getPwConfirm()))
            throw new UnexpectedParamException("mismatched password confirm");

        //닉네임은 2글자 이상으로 제한
        if (joinRequest.getNickname() == null || joinRequest.getNickname().trim().length() < 2)
            throw new UnexpectedParamException("invalid user nickname ["+ joinRequest.getNickname()+"]");

        if (joinRequest.getVerifyAnswer() == null || joinRequest.getVerifyAnswer().trim().length() == 0)
            throw new UnexpectedParamException("invalid verify question answer ["+ joinRequest.getVerifyAnswer()+"]");

        //중복된 아이디 제한
        Optional<User> dupTest = userRepo.findByUserId(joinRequest.getId());
        if (dupTest.isPresent())
            throw new UnexpectedParamException("duplicated user ID ["+ joinRequest.getId()+"]");
        
        //중복된 닉네임 제한
        Optional<User> dupTest2 = userRepo.findByNickname(joinRequest.getNickname());
        if (dupTest2.isPresent())
            throw new UnexpectedParamException("duplicated user nickname ["+ joinRequest.getNickname()+"]");

        //인증 질문 변조 확인
        int vqSeq;
        try {
            vqSeq = Integer.parseInt(joinRequest.getVerifyQuestion());
        } catch (Exception e) {
            throw new UnexpectedParamException("invalid verify question index ["+ joinRequest.getVerifyQuestion()+"]");
        }
        Optional<VerifyQuestion> vq = vqRepo.findBySeq(vqSeq);
        if (!vq.isPresent())
            throw new UnexpectedParamException("invalid verify question index");

        //비밀번호 해싱
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encodedPassword = pe.encode(joinRequest.getPw());
        //System.out.println(encodedPassword);

        User newUser = User.builder()
                .seq(null)
                .userId(joinRequest.getId())
                .nickname(joinRequest.getNickname())
                .verifyQuestion(vq.get())
                .verifyAnswer(joinRequest.getVerifyAnswer())
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
    public void mod(ModRequest modRequest) throws Exception {
        //인증 질문 변조 확인
        int vqSeq;
        try {
            vqSeq = Integer.parseInt(modRequest.getVerifyQuestion());
        } catch (Exception e) {
            throw new UnexpectedParamException("invalid verify question index ["+ modRequest.getVerifyQuestion()+"]");
        }
        Optional<VerifyQuestion> vq = vqRepo.findBySeq(vqSeq);
        if (!vq.isPresent())
            throw new UnexpectedParamException("invalid verify question index");

        //유저 조회 후 ID, 닉네임, 비밀번호를 제외한 정보 수정
        Optional<User> user = userRepo.findByUserId(modRequest.getId());
        user.ifPresent(selectUser -> {
            selectUser.update(vq.get(), modRequest.getVerifyAnswer());
        });
        user.orElseThrow(() -> new UnexpectedParamException("user ID not found"));
        userRepo.save(user.get());

        //비밀번호 해싱
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encodedPassword = pe.encode(modRequest.getPw());
        //이전에 사용했던 비밀번호인지 확인
        List<UserPassword> userPasswords = userPwRepo.findByUser(user.get());
        for (UserPassword up : userPasswords) {
            if (pe.matches(modRequest.getPw(), up.getPassword()))
                throw new UnexpectedParamException("user password that have been used before");
        }

        UserPassword newUserPassword = UserPassword.builder()
                .seq(null)
                .user(user.get())
                .password(encodedPassword)
                .build();
        userPwRepo.saveAndFlush(newUserPassword);
      
      
    }
      
    public User login(String userId, String password) {
        User findUser = userRepository.findByUserId(userId);
        return findUser;
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

}
