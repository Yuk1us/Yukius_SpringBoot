package com.yukius.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserBody {
    private String login,
                   oldPassword,
                   newPassword;
}
