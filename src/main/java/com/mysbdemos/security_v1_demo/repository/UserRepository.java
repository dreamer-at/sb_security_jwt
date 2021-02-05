package com.mysbdemos.security_v1_demo.repository;

import com.mysbdemos.security_v1_demo.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmailAndId(String email, UUID id);
}
