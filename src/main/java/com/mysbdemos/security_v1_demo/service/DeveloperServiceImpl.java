package com.mysbdemos.security_v1_demo.service;

import com.mysbdemos.security_v1_demo.model.Developer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DeveloperServiceImpl implements DeveloperService {

    private final List<Developer> DEVELOPERS = Stream.of(
            new Developer(1L, "Igor", "Nemov"),
            new Developer(2L, "Bella", "Jierin"),
            new Developer(3L, "Vika", "Perto")
    ).collect(Collectors.toList());

    @Override
    public List<Developer> getAll() {
        return DEVELOPERS;
    }

    @Override
    public Developer getById(Long id) {
        return DEVELOPERS.stream().filter(
                developer -> developer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Developer create(Developer developer) {
        this.DEVELOPERS.add(developer);
        return developer;
    }

    @Override
    public void deleteById(Long id) {
        this.DEVELOPERS.removeIf(developer -> developer.getId().equals(id));
    }
}
