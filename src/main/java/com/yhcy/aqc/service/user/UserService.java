package com.yhcy.aqc.service.user;

import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.model.user.VerifyQuestion;
import com.yhcy.aqc.repository.user.UserRepository;
import com.yhcy.aqc.repository.user.VerifyQuestionRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepo;
    private VerifyQuestionRepository vqRepo;

    public UserService(UserRepository userRepo, VerifyQuestionRepository vqRepo) {
        this.userRepo = userRepo;
        this.vqRepo = vqRepo;
    }

    public void joinService(UserVO user) throws Exception {

        //영문(소문자)으로 시작하고 영문(소문자), 숫자를 포함하여 5~20자 제한
        if (user.getId() == null || !user.getId().matches("^[a-z][a-z|_|\\\\-|0-9]{4,19}$"))
            throw new UnexpectedParamException("invalid user ID ["+user.getId()+"]");

        //중복된 아이디 제한
        User dupTest = userRepo.findByUserId(user.getId());
        if (dupTest != null)
            throw new UnexpectedParamException("duplicated user ID ["+user.getId()+"]");

        //8자 이상 영문, 숫자, 특문 모두 포함하도록 제한
        if (user.getPw() == null || !user.getPw().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"))
            throw new UnexpectedParamException("invalid user password");

        //비밀번호와 비밀번호 확인 값이 같은지 확인
        if (user.getPwConfirm() == null || !user.getPw().equals(user.getPwConfirm()))
            throw new UnexpectedParamException("mismatched password confirm");

        //닉네임은 2글자 이상으로 제한
        if (user.getNickname() == null || user.getNickname().trim().length() < 2)
            throw new UnexpectedParamException("invalid user nickname ["+user.getNickname()+"]");
        
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
        VerifyQuestion vq = vqRepo.getOne(vqSeq);
        if (vq == null)
            throw new UnexpectedParamException("invalid verify question index ["+vq+"]");

        //인증 질문 답변은 1글자 이상으로 제한
        if (user.getVerifyAnswer() == null || user.getVerifyAnswer().trim().length() == 0)
            throw new UnexpectedParamException("invalid verify question answer ["+user.getVerifyAnswer()+"]");

        //비밀번호 해싱로직 추가!
        PasswordEncoder pe = new BCryptPasswordEncoder();
        String encodedPassword = pe.encode(user.getPw());
        System.out.println(encodedPassword);
        //비밀번호 확인
        //System.out.println(pe.matches(user.getPw(), encodedPassword));
    }
}
