package com.example.user.model.user;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class LoginRequestModel {
    String email;
    String password;
}
