package com.mysbdemos.security_v1_demo.controller;

import com.mysbdemos.security_v1_demo.model.Developer;
import com.mysbdemos.security_v1_demo.service.DeveloperServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/developers")
public class DeveloperControllerV1 {

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

    private final DeveloperServiceImpl service;

    @GetMapping
    public List<Developer> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}/")
    @PreAuthorize("hasAuthority('developers:read')")
    public Developer getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('developers:write')")
    @ResponseStatus(HttpStatus.OK)
    public Developer create(@RequestBody Developer developer) {
        return service.create(developer);
    }

    @DeleteMapping("/{id}/")
    @PreAuthorize("hasAuthority('developers:write')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}