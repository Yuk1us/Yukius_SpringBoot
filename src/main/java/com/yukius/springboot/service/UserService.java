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
     * @throws org.springframework.dao.DataIntegrityViolationException if User Exists
     * */
    @Transactional
    public User saveUser(User user){
        return userRepository.save(user);
    }


    /**Create
     * <br>
     * Вход пользователя в систему
     * <br>
     * BUG Method Optional{T} findOne(Example{T}) penetrates DataBase with empty @RequestBody User user={}
     * <br>
     * @throws EntityNotFoundException if login/password is wrong

     */
    @Transactional
    public User logInto(User user) throws EntityNotFoundException{
        return userRepository.findByParameters(user.getLogin(),
                                               user.getPassword())
                             .orElseThrow(EntityNotFoundException::new);

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
     * @throws EntityNotFoundException if no matches where found
     * */
    @Transactional
    public User update(UserBody userBody){
        User user = userRepository.findByParameters(userBody.getLogin(),
                                                    userBody.getOldPassword())
                                  .orElseThrow(EntityNotFoundException::new);

        user.setPassword(Objects.requireNonNull(userBody.getNewPassword()));
        return user;
    }


    /**Delete
     * <br>
     * Delete User from DB
     * @throws EntityNotFoundException if no matches where found*/
    @Transactional
    public String deleteUser(User user){
        userRepository.delete(logInto(user));
        return String.format("User: %s No Longer Exists", user.getLogin());
    }
}
