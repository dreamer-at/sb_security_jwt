
package com.mysbdemos.security_v1_demo.controller;

import com.mysbdemos.security_v1_demo.model.User;
import com.mysbdemos.security_v1_demo.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/users")
public class UserControllerV1 {

    /**
     * Responds allowed http methods in the header.
     * <p>
     * This always responds with HTTP-Code 200
     */
    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> collectionOptions() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS)
                .build();
    }

    /**
     * Responds allowed http methods the header.
     * <p>
     * This always responds with HTTP-Code 200
     */
    @RequestMapping(value = "{id}", method = RequestMethod.OPTIONS)
    ResponseEntity<?> singularOptions(@SuppressWarnings("unused") @PathVariable(required = false) UUID id) {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS)
                .build();
    }

    private final UserService service;

    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping("/id")
    public Optional<User> getById(UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(User user) {
        return service.create(user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(UUID id) {
        service.deleteById(id);
    }
}
