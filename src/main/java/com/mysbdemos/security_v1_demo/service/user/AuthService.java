package com.mysbdemos.security_v1_demo.service.user;

import com.mysbdemos.security_v1_demo.dto.AuthenticateDTO;

import java.util.HashMap;

public interface AuthService {

    HashMap<Object, Object> signIn(AuthenticateDTO dto);

    boolean validateToken(String token);
}
