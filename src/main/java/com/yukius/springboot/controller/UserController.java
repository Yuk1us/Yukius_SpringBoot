package com.yukius.springboot.controller;

import com.yukius.springboot.entity.User;
import com.yukius.springboot.entity.UserBody;
import com.yukius.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService hibernateService) {
        this.userService = hibernateService;
    }


    /**
     * Create
     * <br>
     * Регистрация пользователя в сети
     */
    @PostMapping("/signUp")
    public User signUpUser(@RequestBody User user){
        return userService.saveUser(user);
    }


    /**
     * Create
     * <br>
     * Вход пользователя в систему
     */
    @PostMapping("/logInto")
    public User logInto(@RequestBody User user){
        return userService.logInto(user);
    }


    /**Read All Users
     * <br>
     * Получение всех логинов в Базе*/
    @GetMapping("/users")
    @RequestMapping("/users")
    public List<String> getAllUsers(){
        return userService.getAllUsers();
    }


    /**
     * Update
     * <br>
     * Обновление пароля
     */
    @PutMapping("/update")
    public User changePassword(@RequestBody UserBody userBody){
        return userService.update(userBody);
    }



    /**Delete
     * Удаляет одного пользователя*/
    @DeleteMapping("/delete")
    public String deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }

}
