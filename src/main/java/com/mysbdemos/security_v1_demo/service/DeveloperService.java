package com.mysbdemos.security_v1_demo.service;


import com.mysbdemos.security_v1_demo.model.Developer;

import java.util.List;

public interface DeveloperService {

    List<Developer> getAll();

    Developer getById(Long id);

    Developer create(Developer developer);

    void deleteById(Long id);
}
