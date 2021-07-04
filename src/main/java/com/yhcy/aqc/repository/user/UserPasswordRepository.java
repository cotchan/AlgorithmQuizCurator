package com.yhcy.aqc.repository.user;

import com.yhcy.aqc.model.user.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPasswordRepository extends JpaRepository<UserPassword, Integer> {
}
