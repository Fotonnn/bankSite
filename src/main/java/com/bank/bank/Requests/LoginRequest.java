package com.bank.bank.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    private int user_id;
    private String userpassword;
    
}