package com.yukius.springboot.repository;

import com.yukius.springboot.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**Alternative to BUGGED findOne(Example example) and update(UserBody userBody)*/
    @Query("FROM User WHERE login = :userLogin AND password = :userPassword")
    Optional<User> findByParameters(@Param("userLogin") String login, @Param("userPassword") String password);
}
