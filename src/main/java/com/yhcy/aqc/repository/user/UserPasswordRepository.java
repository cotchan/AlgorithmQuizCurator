package com.yhcy.aqc.repository.user;

import com.yhcy.aqc.model.user.User;
import com.yhcy.aqc.model.user.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPasswordRepository extends JpaRepository<UserPassword, Integer> {

    @Query("select up from UserPassword up where up.user = ?1 order by up.createDate desc")
    List<UserPassword> findByUser(User user);

}
