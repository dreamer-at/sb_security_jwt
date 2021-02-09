package com.mysbdemos.security_v1_demo.dto;

import lombok.Data;

@Data
public class AuthenticateDTO {

    private String email;
    private String password;
}
