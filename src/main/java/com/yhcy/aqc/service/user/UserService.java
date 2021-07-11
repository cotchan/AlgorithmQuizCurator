package com.yhcy.aqc.service.user;

import com.yhcy.aqc.model.user.Role;
import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User login(String userId, String password) {
        User findUser = userRepository.findByUserId(userId);
        return findUser;
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

}
