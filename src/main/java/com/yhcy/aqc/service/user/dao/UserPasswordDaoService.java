package com.yhcy.aqc.service.user.dao;

import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.model.user.UserPassword;
import com.yhcy.aqc.repository.user.UserPasswordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@RequiredArgsConstructor
@Service
public class UserPasswordDaoService {

    private final UserPasswordRepository userPasswordRepository;

    public void saveUserPassword(final UserPassword userPassword) {
        userPasswordRepository.saveAndFlush(userPassword);
    }

    public List<UserPassword> findByUser(final User user) {
        checkArgument(user != null, "user must be present");
        return userPasswordRepository.findByUser(user);
    }
}
