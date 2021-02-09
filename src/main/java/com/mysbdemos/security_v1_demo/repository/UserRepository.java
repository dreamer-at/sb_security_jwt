package com.mysbdemos.security_v1_demo.repository;

import com.mysbdemos.security_v1_demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmailAndId(String email, UUID id);
}
