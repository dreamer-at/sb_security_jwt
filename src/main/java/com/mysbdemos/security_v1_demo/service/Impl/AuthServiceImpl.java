package com.mysbdemos.security_v1_demo.service.Impl;


import com.mysbdemos.security_v1_demo.config.security.JwtTokenProvider;
import com.mysbdemos.security_v1_demo.dto.AuthenticateDTO;
import com.mysbdemos.security_v1_demo.model.User;
import com.mysbdemos.security_v1_demo.service.AuthService;
import com.mysbdemos.security_v1_demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager manager;
    private final JwtTokenProvider provider;
    private final UserService service;

    @Override
    public HashMap<Object, Object> signIn(AuthenticateDTO dto) {
        String email = dto.getEmail();
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(email, dto.getPassword()));
        User user = service.findByEmail(dto.getEmail());
        String token = provider.createToken(dto.getEmail(), ""); // user.getRole().name());
        HashMap<Object, Object> response = new HashMap<>();
        response.put("email", dto.getEmail());
        response.put("token", token);
        return response;
    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }
}
