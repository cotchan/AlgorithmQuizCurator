package com.yhcy.aqc.repository.user;

import com.yhcy.aqc.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    @Query(value = "SELECT u from User u join fetch u.verifyQuestion where u.seq = ?1")
    Optional<User> findById(Integer id);

    @Query(value = "SELECT u from User u join fetch u.verifyQuestion where u.userId = ?1")
    Optional<User> findByUserId(String userId);

    @Query(value = "SELECT u from User u join fetch u.verifyQuestion where u.nickname = ?1")
    Optional<User> findByNickname(String nickname);
}
