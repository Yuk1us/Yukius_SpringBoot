package com.yukius.springboot.service;

import com.yukius.springboot.entity.User;
import com.yukius.springboot.entity.UserBody;
import com.yukius.springboot.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class UserService{

    private final UserRepository userRepository;
    @Autowired
    public UserService(final UserRepository userRepositoryImpl){
        this.userRepository = userRepositoryImpl;
    }


    /**Create
     * <br>
     * Регистрация пользователя в сети
     * */
    @Transactional
    public User saveUser(User user){
        return userRepository.save(user);
    }


    /**Create
     * <br>
     * Вход пользователя в систему
     * @return 1.1v Now Returns user OR
     * @throws EntityNotFoundException if login/password is wrong
     * @Bug Method Optional{T} findOne(Example{T}) penetrates DataBase with empty @RequestBody User user={}
     * @Fix Created new Query in Repository
     */
    @Transactional
    public User logInto(User user) throws EntityNotFoundException{
        return userRepository.findByParameters(user.getLogin(), user.getPassword())
                .orElseThrow(()->{return new EntityNotFoundException(
                        "Please enter a correct login and password");
                });
    }


    /**Read All Users
     * <br>
     * @return User's Logins from DB*/
    @Transactional
    public List<String> getAllUsers(){
        List<String> logins = new ArrayList<>();
        userRepository.findAll().forEach(userObj -> {
            logins.add(userObj.getLogin());
        });
        return logins;
    }


    /**Update
     * <br>
     * Password
     * @return User user if login/oldPassword matches
     * @throws EntityNotFoundException if no matches where found
     * @Bug Same Bug occurred in method logInto(User user)
     * @Fix Same Fix
     * */
    @Transactional
    public User update(UserBody userBody){
        User user = userRepository.findByParameters(userBody.getLogin(), userBody.getOldPassword())
                .orElseThrow(()->{return new EntityNotFoundException(
                        "Please enter a correct login and previous password");
                });
        user.setPassword(Objects.requireNonNull(userBody.getNewPassword()));
        return user;

    }


    /**Delete
     * <br>
     * Delete User from DB*/
    @Transactional
    public String deleteUser(User user){
        userRepository.delete(logInto(user));
        return String.format("User: %s No Longer Exists", user.getLogin());
    }
}
