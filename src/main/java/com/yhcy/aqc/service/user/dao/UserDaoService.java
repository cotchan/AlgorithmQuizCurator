package com.yhcy.aqc.service.user.dao;

import com.yhcy.aqc.error.NotFoundException;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.model.user.VerifyQuestion;
import com.yhcy.aqc.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@RequiredArgsConstructor
@Service
public class UserDaoService {

    private final UserRepository userRepository;

    public User findById(final int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, id));
    }

    public User findByUserId(final String userId) {
        checkArgument(userId != null, "userId must be not null");
        return userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(User.class, userId));
    }

    public boolean checkDupByUserId(final String userId) {
        if (userRepository.findByUserId(userId).isPresent())
            return true;
        else
            return false;
    }

    public boolean checkDupByNickname(final String nickname) {
        if (userRepository.findByNickname(nickname).isPresent())
            return true;
        else
            return false;
    }

    public User findByNickname(final String nickname) {
        checkArgument(nickname != null, "nickname must be not null");
        return userRepository.findByNickname(nickname).orElseThrow(() -> new NotFoundException(User.class, nickname));
    }

    @Transactional
    public User saveUser(final User newUser) {
        User resUser = userRepository.save(newUser);
        return resUser;
    }

    public User updateUserByUserId(final String userId, final VerifyQuestion vq, final String vqAnswer) {
        checkArgument(userId != null, "userId must be not null");
        Optional<User> user = userRepository.findByUserId(userId);
        user.ifPresent(selectUser -> {
            selectUser.update(vq, vqAnswer);
        });
        user.orElseThrow(() -> new NotFoundException(User.class, "user ID not found"));
        return userRepository.save(user.get());
    }

}
