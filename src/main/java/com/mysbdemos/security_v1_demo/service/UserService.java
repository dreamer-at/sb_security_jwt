package com.mysbdemos.security_v1_demo.service;


import com.mysbdemos.security_v1_demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<User> getAll();

    Optional<User> getById(UUID id);

    User create(User user);

    void deleteById(UUID id);

    User findByEmail(String email);
}
